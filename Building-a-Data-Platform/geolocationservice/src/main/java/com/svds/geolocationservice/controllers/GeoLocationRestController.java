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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svds.geolocationservice.model.GEOLocation;
import com.svds.geolocationservice.services.KafkaProducerService;
import java.math.BigDecimal;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provide a REST interface for receiving GPS log data
 */
@RestController
@RequestMapping("/geoLocation/v1")
public class GeoLocationRestController {

    @Autowired
    KafkaProducerService kafkaProducerService;
        
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GeoLocationRestController.class);
    private static final String GEO_LOCATION_TOPIC_NAME = "GeoLocation";

    private final GsonBuilder gsonBuilder = new GsonBuilder();

    public static void main(String[] args) {
        SpringApplication.run(GeoLocationRestController.class, args);
    }

    /**
     * Parse geolocation data from REST API
     * 
     * @param id            User ID
     * @param latitude      GPS latitude
     * @param longitude     GPS longitude
     * @param epoch         Log time in unix epoch
     * @param accuracy      GPS accuracy
     * @param requestParams
     * @return GEOLocation Object as JSON
     */
    @RequestMapping(value = "/setGeoLocation/{id}/{latitude}/{longitude}/{epoch}/{accuracy:.+}", method = RequestMethod.PUT)
    public String setGeoLocation(
            @PathVariable final String id,
            @PathVariable final String latitude,
            @PathVariable final String longitude,
            @PathVariable final long epoch,
            @PathVariable final String accuracy,
            @RequestParam MultiValueMap<String, String> requestParams) {

        LOG.info("GeoLocationRestController.setGeoLocation() - start");
        long starttime = System.currentTimeMillis();
        
        GEOLocation geoLocation = new GEOLocation();

        geoLocation.setId(id);
        geoLocation.setLatitude(new BigDecimal(latitude));
        geoLocation.setLongitude(new BigDecimal(longitude));
        geoLocation.setEpoch(epoch);
        geoLocation.setAccuracy(new BigDecimal(accuracy));
        
        kafkaProducerService.sendMessage(GEO_LOCATION_TOPIC_NAME, geoLocation.toString());

        Gson gson = gsonBuilder.setPrettyPrinting().create();

        long endtime = System.currentTimeMillis() - starttime;
        LOG.info("GeoLocationRestController.setGeoLocation() : " + endtime);

        return gson.toJson(geoLocation);
    }
    
    /**
     * Test method for REST server
     * 
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/testValue", method = RequestMethod.GET)
    public String testValue(@RequestParam MultiValueMap<String, String> requestParams){
        
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        LOG.info("at testValue: " + requestParams.toString());
        return gson.toJson(requestParams);
    }

}
