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
package com.svds.resttest.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svds.resttest.model.GenericCountOutput;
import com.svds.resttest.model.GenericResultsOutput;
import com.svds.resttest.services.GenericDataService;
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
 * Provide a rest interface for running queries
 */
@RestController
@RequestMapping("/resettest/v1")
public class TestRestController {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestRestController.class);

    @Autowired
    GenericDataService genericDataService;

    GsonBuilder gsonBuilder = new GsonBuilder();

    public static void main(String[] args) {
        SpringApplication.run(TestRestController.class, args);
    }

    /**
     * Count the number of rows returned by a query
     * 
     * @param databaseEngine    Database engine to query (hive or impala)
     * @param tableName         Table name to query
     * @param requestParams     GET parameters for WHERE clause of query
     * @return                  Row count as JSON
     */
    @RequestMapping(value = "/genericData/count/{databaseEngine}/{tableName}", method = RequestMethod.GET)
    public String genericDataCountEngine(
            @PathVariable final String databaseEngine,
            @PathVariable final String tableName,
            @RequestParam MultiValueMap<String, String> requestParams) {

        long starttime = System.currentTimeMillis();

        Gson gson = gsonBuilder.setPrettyPrinting().create();
        GenericCountOutput genericCountOutput = genericDataService.runQueryCount(databaseEngine, tableName, requestParams);

        long endtime = System.currentTimeMillis() - starttime;
        LOG.info("TestRestController.genericDataDataEngine() : " + endtime);

        genericCountOutput.setProcessTimeMillis(endtime);
        LOG.info(genericCountOutput.toString());

        return gson.toJson(genericCountOutput);
    }

    /**
     * Obtain the results of a query
     * 
     * @param databaseEngine    Database engine to query (hive or impala)
     * @param tableName         Table name to query
     * @param requestParams     GET parameters for WHERE clause of query
     * @return                  Query results as JSON
     */
    @RequestMapping(value = "/genericData/results/{databaseEngine}/{tableName}", method = RequestMethod.GET)
    public String genericDataResultsEngine(
            @PathVariable final String databaseEngine,
            @PathVariable final String tableName,
            @RequestParam MultiValueMap<String, String> requestParams) {

        long starttime = System.currentTimeMillis();

        Gson gson = gsonBuilder.setPrettyPrinting().create();
        GenericResultsOutput genericResultsOutput = genericDataService.runQueryResults(databaseEngine, tableName, requestParams);

        long endtime = System.currentTimeMillis() - starttime;
        LOG.info("TestRestController.genericDataDataEngine() : " + endtime);

        genericResultsOutput.setProcessTimeMillis(endtime);
        LOG.info(genericResultsOutput.toString());

        return gson.toJson(genericResultsOutput);
    }
    
    /**
     * Test the rest interface
     * 
     * @param requestParams     GET parameters
     * @return                  Params as json
     */
    @RequestMapping(value = "/testValue", method = RequestMethod.GET)
    public String testValue(@RequestParam MultiValueMap<String, String> requestParams){
        
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        LOG.info("at testValue: " + requestParams.toString());
        return gson.toJson(requestParams);
    }

}
