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
package com.svds.geolocationservice.controllers;

import com.svds.geolocationservice.services.KafkaProducerService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;
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
public class GeoLocationRestControllerNGTest {
    @Mock
    private KafkaProducerService producer;
    
    private GeoLocationRestController instance;
    
    public GeoLocationRestControllerNGTest() {
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
        
        instance = new GeoLocationRestController();
        instance.kafkaProducerService = producer;
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of setGeoLocation method, of class GeoLocationRestController.
     */
    @Test
    public void testSetGeoLocation() {
        System.out.println("setGeoLocation");
        
        String id = "ASD25423D";
        String latitude = "44.12";
        String longitude = "88.19";
        long epoch = 123000234000L;
        String accuracy = "12.2";
        MultiValueMap<String, String> requestParams = null;
        
        String expResult = "{\n" +
            "  \"id\": \""+id+"\",\n" +
            "  \"latitude\": "+latitude+",\n" +
            "  \"longitude\": "+longitude+",\n" +
            "  \"epoch\": "+epoch+",\n" +
            "  \"accuracy\": "+accuracy+"\n" +
            "}";
        
        String result = instance.setGeoLocation(id, latitude, longitude, epoch, accuracy, requestParams);
        
        assertEquals(result, expResult);
    }
    
}
