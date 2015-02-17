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
import java.util.List;

/**
 * Abstract class for string in set
 */
public abstract class StringInOperatorAbstract implements Operator {

    protected String inProcess(String columnName, List<String> values, String inType) throws OperatorsException {
        if (values.isEmpty()) {
            throw new OperatorsException("For " + inType + " you need more than 1 value");
        }
        StringBuilder returnValue = new StringBuilder();
        returnValue.append(columnName);
        returnValue.append(" ").append(inType).append(" (");

        StringBuilder inValue = new StringBuilder();

        for (String value : values) {
            inValue.append("'").append(value).append("',");
        }
        returnValue.append(inValue.substring(0, inValue.lastIndexOf(",")));
        returnValue.append(") ");
        return returnValue.toString();
    }

    @Override
    public String process(String columnName, List<String> values) throws OperatorsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
