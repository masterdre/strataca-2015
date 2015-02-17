/*
 * Copyright 2015 Silicon Valley Data Science.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.svds.genericconsumer.consumers;

import com.svds.genericconsumer.model.GEOLocation;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;

/**
 * Consume Kafka messages as geolocation data and write to HBase
 */
public class GeoLocationConsumer extends Consumer {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GeoLocationConsumer.class);

    private static final String ZOOKEEPER_QUORUM = "zk";
    private static final String HBASE_TABLE = "hbaseTable";
    protected static final String COLUMN_FAMILY = "locations";

    // Offset of 3 hours because trains stop at 3am
    private static final long CALTRAIN_OFFSET = 3 * 60 * 60 * 1000;

    private static final String MUST_DEFINE = "Must define parameter ";

    private HTable table;

    public GeoLocationConsumer() {

    }

    /**
     * Initialize Consumer with Kafka stream. Requires parameters
     *    zk=zookeeperQuorumServer
     *    hbaseTable=tableName
     * 
     * @param stream        Message stream from Kafka
     * @param threadNumber  ID number of this thread
     * @param parameters    Parameters for hbase connection
     */
    @Override
    public void init(KafkaStream stream, int threadNumber, String[] parameters) {
        super.init(stream, threadNumber, parameters);

        // get parameter arguments
        String zooKeeperQuorum = this.parameters.get(ZOOKEEPER_QUORUM);
        String hbaseTable = this.parameters.get(HBASE_TABLE);

        if (zooKeeperQuorum == null) {
            String error = MUST_DEFINE + ZOOKEEPER_QUORUM + "=zookeeperQuorumServer";
            LOG.error(error);
            throw new IllegalArgumentException(error);
        }
        if (hbaseTable == null) {
            String error = MUST_DEFINE + HBASE_TABLE + "=tableName";
            LOG.error(error);
            throw new IllegalArgumentException(error);
        }

        try {
            setTable(new HTable(createHBaseConfig(zooKeeperQuorum), hbaseTable));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Read messages from Kafka and write to HBase
     */
    @Override
    public void run() {
        LOG.info("Running GeoLocationConsumer Thread: " + super.threadNumber);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            // Try to parse message and write to hbase
            String message = new String(it.next().message());
            LOG.info("Thread " + super.threadNumber + " received message: " + message);
            try {
                GEOLocation geolocation = messageToGEOLocation(message);
                writeGEOLocationToHBase(geolocation);
            } catch (IOException ex) {
                LOG.error("COULD NOT PARSE MESSAGE: " + message);
                LOG.error(ex.getMessage(), ex);
            }

        }
        LOG.info("Shutting down Thread: " + threadNumber);
    }

    /**
     * Parse a message from Kafka stream to GEOLocation object
     *
     * @param message   message to parse
     * @return          GEOLocation object with message data
     * @throws IOException
     */
    protected static GEOLocation messageToGEOLocation(String message) throws IOException {
        try {
            String[] parts = message.split("/");
            String id = parts[0];
            BigDecimal latitude = new BigDecimal(parts[1]);
            BigDecimal longitude = new BigDecimal(parts[2]);
            Long epoch = Long.parseLong(parts[3]);
            BigDecimal accuracy = new BigDecimal(parts[4]);

            GEOLocation geoLocation = new GEOLocation();

            geoLocation.setId(id);
            geoLocation.setLatitude(latitude);
            geoLocation.setLongitude(longitude);
            geoLocation.setEpoch(epoch);
            geoLocation.setAccuracy(accuracy);

            return geoLocation;
        } catch (Exception ex) {
            LOG.error("Error parsing to GEOLocation", ex);
            throw new IOException(ex);
        }
    }

    /**
     * Write GEOLocation object to HBase table
     *
     * @param geolocation Location data to write
     */
    protected void writeGEOLocationToHBase(GEOLocation geolocation) {
        Long epoch = geolocation.getEpoch();
        String id = geolocation.getId();
        byte[] lat = Bytes.toBytes(geolocation.getLatitude().toString());
        byte[] lon = Bytes.toBytes(geolocation.getLongitude().toString());
        byte[] acc = Bytes.toBytes(geolocation.getAccuracy().toString());

        Date timestamp = new Date(geolocation.getEpoch() - CALTRAIN_OFFSET);
        SimpleDateFormat df = new SimpleDateFormat("Y-M-d");
        String dateYMD = df.format(timestamp);

        // unique row key
        String rowKey = dateYMD + "-" + epoch.toString() + "-" + id;

        // Try to write to HBase
        byte[] family = Bytes.toBytes(COLUMN_FAMILY);
        Put p = new Put(Bytes.toBytes(rowKey));
        p.add(family, Bytes.toBytes("epoch"), Bytes.toBytes(epoch.toString()));
        p.add(family, Bytes.toBytes("id"), Bytes.toBytes(id));
        p.add(family, Bytes.toBytes("latitude"), lat);
        p.add(family, Bytes.toBytes("longitude"), lon);
        p.add(family, Bytes.toBytes("accuracy"), acc);

        try {
            table.put(p);
        } catch (InterruptedIOException | RetriesExhaustedWithDetailsException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param zookeeperQuorum   Address of zookeeper quourum server
     * @return                  Configuration for connecting to HBase
     */
    private static Configuration createHBaseConfig(String zookeeperQuorum) {
        Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", zookeeperQuorum);

        return hconf;
    }

    /**
     * Setter for HBase table, exists mostly for testing
     * 
     * @param table HBase table
     */
    protected void setTable(HTable table) {
        this.table = table;
    }

}
