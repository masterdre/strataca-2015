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
package com.svds.resttest.model;

import org.springframework.util.MultiValueMap;

/**
 *
 *
 */
public class GenericCountOutput {

    private String name;
    private long processTimeMillis;
    private String status;
    private String message;
    private MultiValueMap<String, String> requestParams;
    private long count;
    private String sql;
    private String databaseEngine;
    private String tableName;

    /**
     * 
     * @return Name of the query function
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name Name of the query function
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return Time taken to run query (in milliseconds)
     */
    public long getProcessTimeMillis() {
        return processTimeMillis;
    }

    /**
     * 
     * @param processTimeMillis Time taken to run query (in milliseconds)
     */
    public void setProcessTimeMillis(long processTimeMillis) {
        this.processTimeMillis = processTimeMillis;
    }

    /**
     * 
     * @return Status message (OK or ERROR)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status Status message (OK or ERROR)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return Detailed error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message Detailed error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return Parameters used to build WHERE clause
     */
    public MultiValueMap<String, String> getRequestParams() {
        return requestParams;
    }

    /**
     * 
     * @param requestParams Parameters used to build WHERE clause
     */
    public void setRequestParams(MultiValueMap<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    /**
     * 
     * @return Number of rows returned
     */
    public long getCount() {
        return count;
    }

    /**
     * 
     * @param count Number of rows returned
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 
     * @return SQL query string
     */
    public String getSql() {
        return sql;
    }

    /**
     * 
     * @param sql SQL query string
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 
     * @return Database engined used for query
     */
    public String getDatabaseEngine() {
        return databaseEngine;
    }

    /**
     * 
     * @param databaseEngine Database engined used for query
     */
    public void setDatabaseEngine(String databaseEngine) {
        this.databaseEngine = databaseEngine;
    }

    /**
     * 
     * @return Name of table in query
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 
     * @param tableName Name of table in query
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "GenericOutput{" + "name=" + name + ", processTimeMillis=" + processTimeMillis + ", status=" + status + ", message=" + message + ", requestParams=" + requestParams + ", count=" + count + ", sql=" + sql + ", databaseEngine=" + databaseEngine + ", tableName=" + tableName + '}';
    }

}
