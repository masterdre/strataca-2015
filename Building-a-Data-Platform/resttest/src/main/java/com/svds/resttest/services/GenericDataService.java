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
package com.svds.resttest.services;

import com.svds.resttest.model.GenericCountOutput;
import com.svds.resttest.model.GenericResultsOutput;
import com.svds.resttest.model.MetaData;
import com.svds.resttest.operator.BuildWhereClause;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class GenericDataService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GenericDataService.class);
    private static final ResourceBundle DATABASES_BUNDLE = ResourceBundle.getBundle("databases");
    private static final ResourceBundle TABLES_BUNDLE = ResourceBundle.getBundle("tables");
    private static final int ROW_LIMIT = 10;

    /**
     * Build and run a COUNT query
     * 
     * @param databaseEngine    Database engine to query (hive or impala)
     * @param tableName         Table name to query
     * @param requestParams     Parameters for WHERE clause
     * @return                  Output of count query
     */
    public GenericCountOutput runQueryCount(
            String databaseEngine,
            String tableName,
            MultiValueMap<String, String> requestParams) {

        GenericCountOutput genericCountOutput = new GenericCountOutput();
        genericCountOutput.setName("GenericDataService.runQueryCount");
        genericCountOutput.setRequestParams(requestParams);
        genericCountOutput.setDatabaseEngine(databaseEngine);
        genericCountOutput.setTableName(tableName);

        Connection connection = null;
        PreparedStatement pstmt;
        ResultSet rs;

        StringBuilder connectionURL = new StringBuilder();
        connectionURL.append(DATABASES_BUNDLE.getString(databaseEngine));
        LOG.info("connectionURL: " + connectionURL.toString());

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) FROM ");
        sql.append(TABLES_BUNDLE.getString(tableName));

        try {

            LOG.info("RequestParams: " + requestParams.size());
            if (requestParams.size() > 0) {
                sql.append(" WHERE ");
                sql.append(BuildWhereClause.buildWhereClause(requestParams));
            }
            LOG.info("sql: " + sql.toString());
            genericCountOutput.setSql(sql.toString());
            Class.forName("org.apache.hive.jdbc.HiveDriver");

            connection = DriverManager.getConnection(connectionURL.toString(), "hadoop", "");
            pstmt = connection.prepareStatement(sql.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                genericCountOutput.setCount(rs.getInt(1));
                LOG.info("Count: " + rs.getInt(1));
            }

            genericCountOutput.setStatus("OK");
        } catch (Exception e) {
            LOG.error("GenericDataService.runQueryCount(): " + e.getMessage(), e);
            genericCountOutput.setMessage(e.getMessage());
            genericCountOutput.setStatus("ERROR");
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                LOG.error("GenericDataService.runQueryCount() Close connection: " + e.getMessage(), e);
            }
        }

        return genericCountOutput;
    }

    /**
    * Build and run a SELECT query
    * 
    * @param databaseEngine    Database engine to query (hive or impala)
    * @param tableName         Table name to query
    * @param requestParams     Parameters for WHERE clause
    * @return                  Output of select query
    */
    public GenericResultsOutput runQueryResults(
            String databaseEngine,
            String tableName,
            MultiValueMap<String, String> requestParams) {

        GenericResultsOutput genericResultsOutput = new GenericResultsOutput();
        genericResultsOutput.setName("GenericDataService.runQueryCount");
        genericResultsOutput.setRequestParams(requestParams);
        genericResultsOutput.setDatabaseEngine(databaseEngine);
        genericResultsOutput.setTableName(tableName);

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int limit = ROW_LIMIT;
        
        if ( requestParams.containsKey("limit")) {
            limit = new Integer(requestParams.getFirst("limit"));
        }

        StringBuilder connectionURL = new StringBuilder();
        connectionURL.append(DATABASES_BUNDLE.getString(databaseEngine));
        LOG.info("connectionURL: " + connectionURL.toString());

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(TABLES_BUNDLE.getString(tableName));

        try {

            LOG.info("RequestParams: " + requestParams.size());
            if (requestParams.size() > 0) {
                sql.append(" WHERE ");
                sql.append(BuildWhereClause.buildWhereClause(requestParams));
            }
            sql.append(" limit ").append(limit);
            LOG.info("sql: " + sql.toString());
            genericResultsOutput.setSql(sql.toString());
            Class.forName("org.apache.hive.jdbc.HiveDriver");

            connection = DriverManager.getConnection(connectionURL.toString(), "hadoop", "");
            pstmt = connection.prepareStatement(sql.toString());

            rs = pstmt.executeQuery();

            int rowCount = 0;
            while (rs.next()) {

                if (genericResultsOutput.getMetaData().size() > 0) {
                    //Got it!!
                } else {
                    Map<String, Integer> metaDataSet = new HashMap<>();
                    this.getMetaData(rs, metaDataSet, genericResultsOutput);
                }

                List<Object> resultsArrayList = new ArrayList<>();
                this.resultsWithoutMetaData(rs, genericResultsOutput, resultsArrayList);
                genericResultsOutput.getResults().add(resultsArrayList);
                
                rowCount++;
            }

            genericResultsOutput.setCount(rowCount);
            genericResultsOutput.setStatus("OK");
        } catch (Exception e) {
            LOG.error("GenericDataService.runQueryResults(): " + e.getMessage(), e);
            genericResultsOutput.setMessage(e.getMessage());
            genericResultsOutput.setStatus("ERROR");
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                LOG.error("GenericDataService.runQueryResults() Close result set: " + e.getMessage(), e);
            }
            try {
                pstmt.close();
            } catch (Exception e) {
                LOG.error("GenericDataService.runQueryResults() Close prepared statement: " + e.getMessage(), e);
            }
            try {
                connection.close();
            } catch (Exception e) {
                LOG.error("GenericDataService.runQueryResults() Close connection: " + e.getMessage(), e);
            }
        }

        return genericResultsOutput;
    }

    /**
     * Obtain metaDataSet from rs and add to genericResultsOutput
     * 
     * @param rs                    result set containing metadata
     * @param metaDataSet           metadata map to add to
     * @param genericResultsOutput  output object to add metadata to
     * @throws SQLException 
     */
    private void getMetaData(ResultSet rs, Map<String, Integer> metaDataSet, GenericResultsOutput genericResultsOutput) throws SQLException {

        LOG.info(">>>>Starting getMetaData");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<MetaData> metaDataArrayList = genericResultsOutput.getMetaData();

        for (int i = 1; i <= columnCount; i++) {
            metaDataSet.put(rsmd.getColumnName(i), Integer.valueOf(i));
            Map<String, Object> columnAttributes = new HashMap<>();
            columnAttributes.put("ColumnTypeName", rsmd.getColumnTypeName(i));
            columnAttributes.put("IndexNumber", i);
            MetaData metaData = new MetaData();
            metaData.setColumnName(rsmd.getColumnName(i));
            metaData.setColumnAttributes(columnAttributes);

            LOG.info("getColumnType : " + rsmd.getColumnType(i));
            LOG.info("getColumnTypeName : " + rsmd.getColumnTypeName(i));
            LOG.info("index : " + i);

            metaDataArrayList.add(metaData);
        }
        LOG.info(">>>>Ending getMetaData");
    }

    /**
     * Obtain results from query
     * 
     * @param rs                    result set to process
     * @param genericResultsOutput  output object with metadata about these results
     * @param resultsArrayList      result list to add to
     * @throws SQLException 
     */
    private void resultsWithoutMetaData(
            ResultSet rs,
            GenericResultsOutput genericResultsOutput,
            List<Object> resultsArrayList) throws SQLException {

        for (int i = 1; i <= genericResultsOutput.getMetaData().size(); i++) {
            resultsArrayList.add(rs.getObject(i));
        }

    }
}
