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

import junit.framework.TestCase;

import org.seasar.doma.internal.jdbc.mock.ColumnMetaData;
import org.seasar.doma.internal.jdbc.mock.MockConfig;
import org.seasar.doma.internal.jdbc.mock.MockResultSet;
import org.seasar.doma.internal.jdbc.mock.MockResultSetMetaData;
import org.seasar.doma.internal.jdbc.mock.RowData;
import org.seasar.doma.internal.jdbc.query.SelectQuery;
import org.seasar.doma.internal.jdbc.sql.PreparedSql;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.wrapper.StringWrapper;

/**
 * @author taedium
 * 
 */
public class ValueFetcherTest extends TestCase {

    private final MockConfig runtimeConfig = new MockConfig();

    public void testFetchDomain() throws Exception {
        MockResultSetMetaData metaData = new MockResultSetMetaData();
        metaData.columns.add(new ColumnMetaData("aaa"));
        metaData.columns.add(new ColumnMetaData("bbb"));
        metaData.columns.add(new ColumnMetaData("ccc"));
        metaData.columns.add(new ColumnMetaData("ddd"));
        MockResultSet resultSet = new MockResultSet(metaData);
        resultSet.rows.add(new RowData("hoge", "foo", new BigDecimal(10), 100));
        resultSet.next();

        StringWrapper wrapper = new StringWrapper();
        ValueFetcher fetcher = new ValueFetcher(new MySelectQuery());
        fetcher.fetch(resultSet, wrapper);

        assertEquals("hoge", wrapper.get());
    }

    protected class MySelectQuery implements SelectQuery {

        @Override
        public SelectOptions getOptions() {
            return SelectOptions.get();
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
        public PreparedSql getSql() {
            return null;
        }

        @Override
        public int getFetchSize() {
            return 0;
        }

        @Override
        public int getMaxRows() {
            return 0;
        }

        @Override
        public int getQueryTimeout() {
            return 0;
        }

        @Override
        public void prepare() {
        }

        @Override
        public void complete() {
        }

    }
}