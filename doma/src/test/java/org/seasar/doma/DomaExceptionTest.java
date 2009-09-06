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
package org.seasar.doma;

import org.seasar.doma.DomaNullPointerException;
import org.seasar.doma.domain.BuiltinStringDomain;
import org.seasar.doma.message.DomaMessageCode;

import junit.framework.TestCase;

/**
 * @author taedium
 * 
 */
public class DomaExceptionTest extends TestCase {

    public void testE0001() throws Exception {
        BuiltinStringDomain domain = new BuiltinStringDomain("aaa");
        try {
            domain.compareTo(null);
            fail();
        } catch (DomaNullPointerException e) {
            System.out.println(e.getMessage());
            assertEquals(DomaMessageCode.DOMA0001, e.getMessageCode());
        }
    }

    public void testE0002() throws Exception {
    }
}
