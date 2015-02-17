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

import kafka.consumer.ConsumerIterator;
import org.slf4j.LoggerFactory;

/**
 * Simple Kafka consumer which only logs messages to file
 */
public class BasicConsumer extends Consumer {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BasicConsumer.class);
    
    public BasicConsumer() {
        
    }

    /**
     * Log everything from stream to file
     */
    @Override
    public void run() {
        LOG.info("HELLO... from BasicConsumer: " + super.threadNumber);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());
            
            LOG.info("HELLO ----- Thread " + super.threadNumber + ": " + message);
            System.out.println("CONSUMER: " + message);
            
        }
        LOG.info("Shutting down Thread: " + threadNumber);
        
    }
    
    
    
}