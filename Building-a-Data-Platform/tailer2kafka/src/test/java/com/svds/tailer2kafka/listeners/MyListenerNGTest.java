/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.svds.tailer2kafka.listeners;

import com.svds.tailer2kafka.services.KafkaProducerService;
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

public class MyListenerNGTest {
    @Mock
    private KafkaProducerService service;
    
    public MyListenerNGTest() {
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
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of handle method, of class MyListener.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        String line = "testline";
        String topic = "myTopic";
        
        MyListener instance = new MyListener();
        instance.setKafkaProducerService(service);
        instance.setTopicName(topic);
        instance.handle(line);
        
        ArgumentCaptor<String> topicArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lineArg = ArgumentCaptor.forClass(String.class);
        Mockito.verify(service).sendMessage(topicArg.capture(), lineArg.capture());
        
        assertEquals(topicArg.getValue(), topic);
        assertEquals(lineArg.getValue(), line);
    }

    /**
     * Test of shutdown method, of class MyListener.
     */
    @Test
    public void testShutdown() {
        System.out.println("shutdown");
        MyListener instance = new MyListener();
        instance.setKafkaProducerService(service);
        instance.shutdown();
        
        Mockito.verify(service).close();
    }

    /**
     * Test of setTopicName method, of class MyListener.
     */
    @Test
    public void testSetTopicName() {
        System.out.println("setTopicName");
        String topicName = "myTopic";
        MyListener instance = new MyListener();
        instance.setTopicName(topicName);
        
        assertEquals(topicName, instance.getTopicName());
    }
    
}
