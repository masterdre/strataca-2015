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

import com.svds.genericconsumer.consumers.BasicConsumer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.hadoop.hbase.util.Bytes;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
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
public class BasicConsumerNGTest {
    @Mock 
    private KafkaStream kafkaStream;
    @Mock
    private ConsumerIterator<byte[], byte[]> streamIterator;
    @Mock
    private MessageAndMetadata<byte[], byte[]> messageAndMetadata;
    
    public BasicConsumerNGTest() {
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
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of run method, of class BasicConsumer.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        BasicConsumer instance = new BasicConsumer();
        instance.init(kafkaStream,0,new String[0]);
        instance.run();
    }
    
}
