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
package com.svds.resttest.exceptions;

/**
 * Class exception for error building SQL WHERE clause
 */
public class BuildWhereException extends Exception {
    
    public BuildWhereException() {
        super();
    }

    public BuildWhereException(String message) {
        super(message);
    }

    public BuildWhereException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BuildWhereException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildWhereException(Throwable cause) {
        super(cause);
    }

}
