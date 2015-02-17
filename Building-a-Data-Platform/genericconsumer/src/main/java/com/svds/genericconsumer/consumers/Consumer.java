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

import java.util.HashMap;
import java.util.Map;
import kafka.consumer.KafkaStream;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for consuming Kafka messages
 */
public abstract class Consumer implements Runnable {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GeoLocationConsumer.class);
    
    protected KafkaStream stream;
    protected int threadNumber;
    protected Map<String,String> parameters;
    
    /**
     * Initialize Consumer with Kafka stream and optional parameters
     * 
     * @param stream        Message stream from Kafka
     * @param threadNumber  ID number of this thread
     * @param parameters    Extra parameters to parse, in the form key=value
     */
    public void init(KafkaStream stream, int threadNumber, String[] parameters){
        this.threadNumber = threadNumber;
        this.stream = stream;
        this.parameters = new HashMap<>();
        
        LOG.info("threadNumber: " + this.threadNumber);
        for (String param : parameters) {
            try {
                String[] keyval = param.split("=");
                this.parameters.put(keyval[0], keyval[1]);
            } catch (Exception e) {
                LOG.error("Could not parse param into key=val");
                LOG.error(e.getMessage(),e);
            }
            LOG.info("parameter: " + param);
        }
        
    }
    
}
