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
package com.svds.tailer2kafka.main;

import com.svds.tailer2kafka.listeners.MyListener;
import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.input.Tailer;
import org.slf4j.LoggerFactory;

public class Main implements ShutdownThreadParent {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String TOPICNAME = "topicname";
    private static final String FILENAME = "filename";
    private static final String METADATABROKERLIST = "metadatabrokerlist";

    private Tailer tailer;
    private ShutdownThread shutdownThread;
    private MyListener listener;

    /**
     * Given command-line arguments, create GenericConsumerGroup
     * 
     * @param args  command-line arguments to parse
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        Options options = new Options();

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(TOPICNAME)
                .withDescription("Kafka topic name")
                .hasArg()
                .create());
        
        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(METADATABROKERLIST)
                .withDescription("Kafka metadata.broker.list")
                .hasArg()
                .create());

        options.addOption(OptionBuilder
                .isRequired()
                .withLongOpt(FILENAME)
                .withDescription("Log filename")
                .hasArg()
                .create());

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);

        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("com.svds.tailer2kafka.main.Main", options);

            throw new IOException("Missing Args");
        }

        Main main = new Main();
        main.doWork(cmd);
    }

    /**
     * Run tailer until interrupted
     * 
     * @param cmd
     */
    public void doWork(CommandLine cmd) throws InterruptedException {
        shutdownThread = new ShutdownThread(this);
        Runtime.getRuntime().addShutdownHook(shutdownThread);
        listener = new MyListener();
        listener.setTopicName(cmd.getOptionValue(TOPICNAME));
        listener.setMetadataBrokerList(cmd.getOptionValue(METADATABROKERLIST));
        tailer = Tailer.create(new File(cmd.getOptionValue(FILENAME)), listener, 500);
        while (true) {
            
            Thread.sleep(100000);

        }
    }

    /**
     * Close the tailer and listener
     */
    @Override
    public void shutdown() {
        tailer.stop();
        listener.shutdown();

    }
}
