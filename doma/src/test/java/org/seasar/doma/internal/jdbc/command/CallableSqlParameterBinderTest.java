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
package org.seasar.doma.internal.jdbc.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.seasar.doma.domain.BuiltinBigDecimalDomain;
import org.seasar.doma.domain.BuiltinIntegerDomain;
import org.seasar.doma.domain.BuiltinStringDomain;
import org.seasar.doma.internal.jdbc.command.CallableSqlParameterBinder;
import org.seasar.doma.internal.jdbc.mock.BindValue;
import org.seasar.doma.internal.jdbc.mock.MockCallableStatement;
import org.seasar.doma.internal.jdbc.mock.MockConfig;
import org.seasar.doma.internal.jdbc.mock.RegisterOutParameter;
import org.seasar.doma.internal.jdbc.query.Query;
import org.seasar.doma.internal.jdbc.sql.CallableSqlParameter;
import org.seasar.doma.internal.jdbc.sql.DomainResultParameter;
import org.seasar.doma.internal.jdbc.sql.InOutParameter;
import org.seasar.doma.internal.jdbc.sql.InParameter;
import org.seasar.doma.internal.jdbc.sql.OutParameter;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.Sql;

import junit.framework.TestCase;

/**
 * @author taedium
 * 
 */
public class CallableSqlParameterBinderTest extends TestCase {

    private MockConfig runtimeConfig = new MockConfig();

    public void testBind() throws Exception {
        MockCallableStatement callableStatement = new MockCallableStatement();

        List<CallableSqlParameter> parameters = new ArrayList<CallableSqlParameter>();
        parameters.add(new DomainResultParameter<BuiltinIntegerDomain>(
                BuiltinIntegerDomain.class));
        parameters.add(new InParameter(new BuiltinStringDomain("aaa")));
        parameters.add(new InOutParameter(new BuiltinBigDecimalDomain(new BigDecimal(
                10))));
        parameters.add(new OutParameter(new BuiltinStringDomain("bbb")));
        CallableSqlParameterBinder binder = new CallableSqlParameterBinder(
                new MyQuery());
        binder.bind(callableStatement, parameters);

        List<BindValue> bindValues = callableStatement.bindValues;
        assertEquals(2, bindValues.size());
        BindValue bindValue = bindValues.get(0);
        assertEquals(2, bindValue.getIndex());
        assertEquals("aaa", bindValue.getValue());
        bindValue = bindValues.get(1);
        assertEquals(3, bindValue.getIndex());
        assertEquals(new BigDecimal(10), bindValue.getValue());
        List<RegisterOutParameter> registerOutParameters = callableStatement.registerOutParameters;
        assertEquals(3, registerOutParameters.size());
    }

    protected class MyQuery implements Query {

        @Override
        public Sql<?> getSql() {
            return null;
        }

        @Override
        public Config getConfig() {
            return runtimeConfig;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public String getMethodName() {
            return null;
        }

        @Override
        public int getQueryTimeout() {
            return 0;
        }

    }
}
