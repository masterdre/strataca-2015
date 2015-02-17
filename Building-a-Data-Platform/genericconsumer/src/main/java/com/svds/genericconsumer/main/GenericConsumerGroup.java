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
package com.svds.genericconsumer.main;

import com.svds.genericconsumer.consumers.Consumer;
import java.io.IOException;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.LoggerFactory;

/**
 * Main class for creating Kafka consumer threads
 */
public class GenericConsumerGroup implements ShutdownThreadParent {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GenericConsumerGroup.class);
    private static final String ZOOKEEPER = "zookeeper";
    private static final String TOPICNAME = "topicname";
    private static final String GROUPID = "groupid";
    private static final String THREADS = "threads";
    private static final String CONSUMERCLASS = "consumerclass";
    private static final String PARAMETERS = "parameters";

    private ConsumerConnector consumer;
    private ExecutorService executor;
    private ShutdownThread shutdownThread;

    @Override
    public void shutdown() {
        if (consumer != null) {
            consumer.shutdown();
        }
        if (executor != null) {
            executor.shutdown();
        }
    }

    /**
     * Create Kafka message streams to and spawn threads of Consumer objects to
     * listen to Kafka streams
     * 
     * @param topic         Kafka topic to listen to
     * @param numThreads    Number of Kafka listener threads to spawn
     * @param consumerClass Implementing class of consumer object
     * @param parameters    Optional parameters for consumer object
     * @throws IOException 
     */
    public void run(String topic, int numThreads, String consumerClass, String[] parameters) throws IOException {
        try {
            LOG.info("HELLO from run");

            Map<String, Integer> topicCountMap = new HashMap<>();
            topicCountMap.put(topic, numThreads);
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
            List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

            // now launch all the threads
            //
            executor = Executors.newFixedThreadPool(numThreads);

            // now create an object to consume the messages
            //
            int threadNumber = 0;
            for (final KafkaStream stream : streams) {
                Consumer consumerObject = (Consumer) Class.forName(consumerClass).newInstance();
                consumerObject.init(stream, threadNumber, parameters);
                executor.submit(consumerObject);
                threadNumber++;
            }
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            LOG.error("Error running threads in GenericConsumerGroup");
            throw new IOException(ex);
        }
    }
    
    /**
     * Returns a ConsumerConfig with connection information for Kafka
     * 
     * @param zookeeper zookeeper server list to connect to
     * @param groupId   unique id for this consumer group
     * @return          the configured connection information
     */
    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }

    /**
     * Given command-line arguments, create GenericConsumerGroup
     * 
     * @param args  command-line arguments to parse
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        LOG.info("HELLO from main");
        
        Options options = new Options();
        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(ZOOKEEPER)
                .withDescription("Zookeeper servers")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(GROUPID)
                .withDescription("Kafka group id")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(TOPICNAME)
                .withDescription("Kafka topic name")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(THREADS)
                .withType(Number.class)
                .withDescription("Number of threads")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(CONSUMERCLASS)
                .withDescription("Consumer Class from processing topic name")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .withLongOpt(PARAMETERS)
                .withDescription("Parameters needed for the consumer Class if needed")
                .hasArgs()
                .withValueSeparator(',')
                .create());

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            LOG.error(e.getMessage(),e);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("GenericConsumerGroup.main", options);
            throw new IOException(e);
        }

        try {
            GenericConsumerGroup consumerGroupExample = new GenericConsumerGroup();
            consumerGroupExample.doWork(cmd);
        } catch (ParseException ex) {
            LOG.error("Error parsing command-line args");
            throw new IOException(ex);
        }
    }

    /**
     * Helper for main for parsing command-line arguments and running
     * the ConsumerGroup
     * 
     * @param cmd
     * @throws IOException
     * @throws ParseException 
     */
    private void doWork(CommandLine cmd) throws IOException, ParseException {
        LOG.info("HELLO from doWork");

        shutdownThread = new ShutdownThread(this);
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(cmd.getOptionValue(ZOOKEEPER), cmd.getOptionValue(GROUPID)));

        run(cmd.getOptionValue(TOPICNAME),
                ((Number) cmd.getParsedOptionValue(THREADS)).intValue(),
                cmd.getOptionValue(CONSUMERCLASS),
                (cmd.getOptionValue(PARAMETERS) == null) ? new String[0] : cmd.getOptionValues(PARAMETERS));
    }
}
