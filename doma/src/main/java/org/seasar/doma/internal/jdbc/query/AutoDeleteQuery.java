/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.doma.internal.jdbc.query;

import static org.seasar.doma.internal.util.AssertionUtil.*;

import org.seasar.doma.internal.jdbc.entity.AbstractPostDeleteContext;
import org.seasar.doma.internal.jdbc.entity.AbstractPreDeleteContext;
import org.seasar.doma.internal.jdbc.sql.PreparedSqlBuilder;
import org.seasar.doma.jdbc.SqlKind;
import org.seasar.doma.jdbc.entity.EntityPropertyType;
import org.seasar.doma.jdbc.entity.EntityType;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;

/**
 * @author taedium
 * 
 */
public class AutoDeleteQuery<E> extends AutoModifyQuery<E> implements
        DeleteQuery {

    protected boolean versionIgnored;

    protected boolean optimisticLockExceptionSuppressed;

    public AutoDeleteQuery(EntityType<E> entityType) {
        super(entityType);
    }

    @Override
    public void prepare() {
        assertNotNull(config, entityType, callerClassName, callerMethodName);
        executable = true;
        preDelete();
        prepareIdAndVersionPropertyTypes();
        validateIdExistent();
        prepareOptions();
        prepareOptimisticLock();
        prepareSql();
        assertNotNull(sql);
    }

    protected void preDelete() {
        PreDeleteContext context = new AutoPreDeleteContext(entityType);
        entityType.preDelete(entity, context);
    }

    protected void prepareOptimisticLock() {
        if (versionPropertyType != null && !versionIgnored) {
            if (!optimisticLockExceptionSuppressed) {
                optimisticLockCheckRequired = true;
            }
        }
    }

    protected void prepareSql() {
        PreparedSqlBuilder builder = new PreparedSqlBuilder(config,
                SqlKind.DELETE);
        builder.appendSql("delete from ");
        builder.appendSql(entityType.getQualifiedTableName());
        if (idPropertyTypes.size() > 0) {
            builder.appendSql(" where ");
            for (EntityPropertyType<E, ?> p : idPropertyTypes) {
                builder.appendSql(p.getColumnName());
                builder.appendSql(" = ");
                builder.appendWrapper(p.getWrapper(entity));
                builder.appendSql(" and ");
            }
            builder.cutBackSql(5);
        }
        if (versionPropertyType != null && !versionIgnored) {
            if (idPropertyTypes.size() == 0) {
                builder.appendSql(" where ");
            } else {
                builder.appendSql(" and ");
            }
            builder.appendSql(versionPropertyType.getColumnName());
            builder.appendSql(" = ");
            builder.appendWrapper(versionPropertyType.getWrapper(entity));
        }
        sql = builder.build();
    }

    @Override
    public void complete() {
        postDelete();
    }

    protected void postDelete() {
        PostDeleteContext context = new AutoPostDeleteContext(entityType);
        entityType.postDelete(entity, context);
    }

    public void setVersionIgnored(boolean versionIgnored) {
        this.versionIgnored = versionIgnored;
    }

    public void setOptimisticLockExceptionSuppressed(
            boolean optimisticLockExceptionSuppressed) {
        this.optimisticLockExceptionSuppressed = optimisticLockExceptionSuppressed;
    }

    protected static class AutoPreDeleteContext extends
            AbstractPreDeleteContext {

        public AutoPreDeleteContext(EntityType<?> entityType) {
            super(entityType);
        }
    }

    protected static class AutoPostDeleteContext extends
            AbstractPostDeleteContext {

        public AutoPostDeleteContext(EntityType<?> entityType) {
            super(entityType);
        }
    }
}
