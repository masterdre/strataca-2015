/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.tailer2kafka.services;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author kevin
 */
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
        
        KafkaProducerService instance = new KafkaProducerService("localhost:9092");
        instance.setKafkaProducer(kafkaProducer);
        
        instance.sendMessage(topicName, message);
        
        ArgumentCaptor<KeyedMessage> arg = ArgumentCaptor.forClass(KeyedMessage.class);
        Mockito.verify(kafkaProducer).send(arg.capture());
        
        assertEquals(arg.getValue().message(), message);
                
    }

    /**
     * Test of close method, of class KafkaProducerService.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        KafkaProducerService instance = new KafkaProducerService("localhost:9092");
        instance.setKafkaProducer(kafkaProducer);
        instance.close();
        
        Mockito.verify(kafkaProducer).close();
    }
    
}
