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
package com.svds.tailer2kafka.listeners;

import com.svds.tailer2kafka.services.KafkaProducerService;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.LoggerFactory;

/**
 * Listen to changes on file and log them to Kafka
 */
public class MyListener extends TailerListenerAdapter {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MyListener.class);
    private KafkaProducerService kafkaProducerService;
    private String topicName;
    private String metadataBrokerList;

    @Override
    public void init(Tailer tailer) {
        super.init(tailer);
        LOG.info("<<<Started MyListener>>");
        kafkaProducerService = new KafkaProducerService(this.metadataBrokerList);
    }

    @Override
    public void handle(String line) {
        this.kafkaProducerService.sendMessage(this.topicName, line);
    }

    /**
     * Close Kafka Producer connection
     */
    public void shutdown() {
        kafkaProducerService.close();
    }

    /**
     * Set the Kafka topic
     * 
     * @param topicName Kafka topic name to log messages on
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    /**
     * @return the Kafka topic for this listener
     */
    public String getTopicName() {
        return this.topicName;
    }

    /**
     *
     * @return
     */
    public String getMetadataBrokerList() {
        return metadataBrokerList;
    }

    /**
     *
     * @param metadataBrokerList
     */
    public void setMetadataBrokerList(String metadataBrokerList) {
        this.metadataBrokerList = metadataBrokerList;
    }
    
    /**
     * Set Kafka Producer service, mainly for unit tests
     * 
     * @param kafkaProducerService 
     */
    protected void setKafkaProducerService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }
}
