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
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GeoLocationConsumerNGTest {
    @Mock 
    private KafkaStream kafkaStream;
    @Mock
    private ConsumerIterator<byte[], byte[]> streamIterator;
    @Mock
    private MessageAndMetadata<byte[], byte[]> messageAndMetadata;
    @Mock
    private HTable table;
    
    private GeoLocationConsumer instance;
    
    public GeoLocationConsumerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        when(kafkaStream.iterator()).thenReturn(streamIterator);
        when(streamIterator.next()).thenReturn(messageAndMetadata);
        when(messageAndMetadata.message()).thenReturn(Bytes.toBytes("122309470239808/43.12/88.22/102/1.46"));
        
        int threadNumber = 0;
        String[] parameters = {"zk=zookeeperservr","hbaseTable=tableName"};
        
        instance = new GeoLocationConsumer();
        instance.init(kafkaStream, threadNumber, parameters);
        
        instance.setTable(table);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of run method, of class GeoLocationConsumer.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        instance.run();
    }

    /**
     * Test of messageToGEOLocation method, of class GeoLocationConsumer.
     */
    @Test
    public void testMessageToGEOLocation() throws Exception {
        System.out.println("messageToGEOLocation");
        
        String message = "122309470239808/43.12/88.22/102/1.46";
        
        GEOLocation result = GeoLocationConsumer.messageToGEOLocation(message);
        
        assertEquals(result.getId(), "122309470239808");
        assertEquals(result.getLatitude(), new BigDecimal("43.12"));
        assertEquals(result.getLongitude(), new BigDecimal("88.22"));
        assertEquals(result.getEpoch(), 102);
        assertEquals(result.getAccuracy(), new BigDecimal("1.46"));
    }

    /**
     * Test of writeGEOLocationToHBase method, of class GeoLocationConsumer.
     */
    @Test
    public void testWriteGEOLocationToHBase() throws InterruptedIOException, RetriesExhaustedWithDetailsException {
        System.out.println("writeGEOLocationToHBase");
        
        GEOLocation loc = new GEOLocation();
        
        String id = "122309470239808";
        Long epoch = 102L;
        BigDecimal lat = new BigDecimal("43.12");
        BigDecimal lon = new BigDecimal("88.22");
        BigDecimal acc = new BigDecimal("1.46");
        
        loc.setId(id);
        loc.setLatitude(lat);
        loc.setLongitude(lon);
        loc.setEpoch(epoch);
        loc.setAccuracy(acc);
        
        instance.writeGEOLocationToHBase(loc);
        
        ArgumentCaptor<Put> arg = ArgumentCaptor.forClass(Put.class);
        Mockito.verify(table).put(arg.capture());
        
        // make sure the row contains the latitude, longitude, and accuracy we expect
        byte[] family = Bytes.toBytes(GeoLocationConsumer.COLUMN_FAMILY);
        assertTrue(arg.getValue().has(family, Bytes.toBytes("latitude"), Bytes.toBytes(lat.toString())));
        assertTrue(arg.getValue().has(family, Bytes.toBytes("longitude"), Bytes.toBytes(lon.toString())));
        assertTrue(arg.getValue().has(family, Bytes.toBytes("accuracy"), Bytes.toBytes(acc.toString())));
        assertTrue(arg.getValue().has(family, Bytes.toBytes("id"), Bytes.toBytes(id)));
        assertTrue(arg.getValue().has(family, Bytes.toBytes("epoch"), Bytes.toBytes(epoch.toString())));
        
    }
    
}
