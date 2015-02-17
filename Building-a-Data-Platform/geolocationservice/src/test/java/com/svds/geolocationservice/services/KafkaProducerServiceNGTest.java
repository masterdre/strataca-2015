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

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;

public class KafkaProducerServiceNGTest {
    @Mock
    private Producer<String,String> kafkaProducer;
    
    public KafkaProducerServiceNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of sendMessage method, of class KafkaProducerService.
     */
    @org.testng.annotations.Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String topicName = "trains";
        String message = "1/1/1/1/1";
        
        KafkaProducerService instance = new KafkaProducerService();
        instance.setKafkaProducer(kafkaProducer);
        
        instance.sendMessage(topicName, message);
        
        ArgumentCaptor<KeyedMessage> arg = ArgumentCaptor.forClass(KeyedMessage.class);
        Mockito.verify(kafkaProducer).send(arg.capture());
        
        assertEquals(arg.getValue().message(), message);
                
    }
    
}
