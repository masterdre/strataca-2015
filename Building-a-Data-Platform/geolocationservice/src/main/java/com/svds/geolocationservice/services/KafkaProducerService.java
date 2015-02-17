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
package com.svds.geolocationservice.services;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Create a Producer connection to Kafka
 */
@Service
public class KafkaProducerService {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);
    public static final String KAFKA_CONFIG = "kafkaConfig";
        
    private ProducerConfig kafkaProducerConfig;
    private Producer<String, String> kafkaProducer;
    

    /**
     * Create a producer connection to Kafka
     */
    public KafkaProducerService() {
        LOG.info("KafkaProducerService - start");
        long starttime = System.currentTimeMillis();
        
        try {
            Properties kafkaProducerProps = new Properties();
            ResourceBundle kafkaProducerBundle = ResourceBundle.getBundle(KafkaProducerService.KAFKA_CONFIG);

            Enumeration<String> keys = kafkaProducerBundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                kafkaProducerProps.put(key, kafkaProducerBundle.getString(key));
            }

            kafkaProducerConfig = new ProducerConfig(kafkaProducerProps);
            setKafkaProducer(new Producer<String,String>(kafkaProducerConfig));
                    
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        long endtime = System.currentTimeMillis() - starttime;
        LOG.info("KafkaProducerService : " + endtime);
    }
    
    /**
     * Send a message to Kafka
     * 
     * @param topicName Kafka topic for this message
     * @param message   message to send to Kafka
     */
    public void sendMessage(String topicName, String message) {
        LOG.info("KafkaProducerService.sendMessage() - start");
        long starttime = System.currentTimeMillis();
        LOG.info("sendMessage");
        KeyedMessage<String, String> data = new KeyedMessage<>(topicName, message);
        LOG.info("sendMessage: KeyedMessage: Done");
        this.kafkaProducer.send(data);
        LOG.info("sendMessage: Send: Done");
        long endtime = System.currentTimeMillis() - starttime;
        LOG.info("KafkaProducerService.sendMessage() : " + endtime);
    }

    /**
     * Mainly for unit-test purpose of setting a mock Kafka producer
     *
     * @param producer Kafka producer to be set
     */
    protected void setKafkaProducer(Producer<String, String> producer) {
        this.kafkaProducer = producer;
    }
}
