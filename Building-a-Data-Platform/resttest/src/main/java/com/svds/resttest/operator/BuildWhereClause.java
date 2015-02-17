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
package com.svds.resttest.operator;

import com.svds.resttest.exceptions.BuildWhereException;
import com.svds.resttest.exceptions.OperatorsException;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author steveo
 */
public class BuildWhereClause {
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BuildWhereClause.class);
    
    private static final String OPERATOR_TAG = "operator__";
    private static final String COLUMN_TAG = "column__";
    private static final String AND = " AND ";
    
    private BuildWhereClause() {
        
    }
    
    /**
     * Create a WHERE clause given parameters from GET request
     * 
     * @param requestParams query parameters form GET request
     * @return SQL WHERE clause
     * @throws BuildWhereException 
     */
    public static String buildWhereClause(MultiValueMap<String, String> requestParams) throws BuildWhereException {
        
        Set<String> columnNames = new HashSet<>();
        for (String key : requestParams.keySet()) {
            for (String value : requestParams.get(key)) {
                LOG.info("key:" + key + "  :  value:" + value);
            }
            if (key.startsWith(COLUMN_TAG)) {
                LOG.info("key - substr: " + key.substring(8));
                
                columnNames.add(key.substring(8));
            }
        }
        
        StringBuilder whereClause = new StringBuilder();
        
        for(String value : columnNames) {
            LOG.info("Column Name: " + value);
            LOG.info("Got this operator: " + requestParams.containsKey(OPERATOR_TAG+value));
            LOG.info("Got this operator - value: " + requestParams.getFirst(OPERATOR_TAG+value));
            LOG.info("Got this column: " + requestParams.containsKey(COLUMN_TAG+value));
            LOG.info("Got this column - value: " + requestParams.get(COLUMN_TAG+value));
            
            try {
                String operatorValue = requestParams.getFirst(OPERATOR_TAG+value);
                Operator operator = OperatorFactory.getOperatorClass(operatorValue);
                String operatorProcessValue;
                operatorProcessValue = operator.process(value, requestParams.get(COLUMN_TAG+value));
                whereClause.append(operatorProcessValue).append(AND);
            } catch (OperatorsException ex) {
                LOG.error("OperatorException: " + ex.getMessage(),ex);
                throw new BuildWhereException(ex);
            }
            
        }
        
        LOG.info("Value whereClause: " + whereClause );
        
        String returnValue = whereClause.substring(0, whereClause.lastIndexOf(AND));
        LOG.info("WhereClause: " + returnValue);
        return returnValue;
    }
}
