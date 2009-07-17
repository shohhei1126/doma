/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.converter;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author taedium
 * 
 */
public class NumberConverter {

    protected static String DEFAULT_PATTERN = "#";

    protected Number parse(String value, String pattern) {
        String p = pattern != null ? pattern : DEFAULT_PATTERN;
        DecimalFormat decimalFormat = new DecimalFormat(p);
        try {
            return decimalFormat.parse(value);
        } catch (ParseException e) {
            throw new org.seasar.doma.converter.ParseConversionException(Number.class
                    .getName(), e);
        }
    }
}