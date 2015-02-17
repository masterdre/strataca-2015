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

import com.svds.resttest.exceptions.OperatorsException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author steveo
 */
public class OperatorFactory {

    private static final Logger LOG = LoggerFactory.getLogger(OperatorFactory.class);
    private static final Map<String, Operator> OPERATOR_HASH_MAP = new HashMap<>();
    private static final ResourceBundle OPERATOR_BUNDLE = ResourceBundle.getBundle("operators");
    private static final OperatorFactory OPERATOR_FACTORY = new OperatorFactory();

    private OperatorFactory() {
        LOG.info("Start!!!");
        Enumeration<String> keys = OPERATOR_BUNDLE.getKeys();

        while (keys.hasMoreElements()) {
            try {
                String value = keys.nextElement();
                LOG.info(OPERATOR_BUNDLE.getString(value));
                OPERATOR_HASH_MAP.put(value, (Operator) Class.forName(OPERATOR_BUNDLE.getString(value)).newInstance());
            } catch (ClassNotFoundException ex) {
                LOG.error("Class not found: ", ex);
            } catch (InstantiationException ex) {
                LOG.error("Instantiation: ", ex);
            } catch (IllegalAccessException ex) {
                LOG.error("Illegal access: ", ex);
            }

        }
        LOG.info("End!!!");
    }

    /**
     * Get class path from operator type
     * 
     * @param value Operator type
     * @return      Class path of operator type
     */
    public static String getInfo(String value) {
        LOG.debug("OPERATOR_FACTORY: " + OPERATOR_FACTORY.toString());
        LOG.info("OPERATOR_BUNDLE: " + OPERATOR_BUNDLE.getString(value.toUpperCase()));
        return OPERATOR_BUNDLE.getString(value.toUpperCase());
    }

    /**
     * Get an operator of a specific class
     * 
     * @param operatorType  Name of operator to find
     * @return              Specific operator object
     * @throws com.svds.resttest.exceptions.OperatorsException
     */
    public static Operator getOperatorClass(String operatorType) throws OperatorsException {
        LOG.info("operatorType: " + operatorType);
        if (operatorType == null) {
            throw new OperatorsException("Operator is null");
        }
        LOG.info("getOperatorClass: " + operatorType);
        LOG.info("containsKey : " + OPERATOR_HASH_MAP.containsKey(operatorType.toUpperCase()));
        Operator operator = OPERATOR_HASH_MAP.get(operatorType.toUpperCase());
        if (operator == null) {
            throw new OperatorsException("Couldn't find operator: " + operatorType);
        }
        return operator;
    }

}
