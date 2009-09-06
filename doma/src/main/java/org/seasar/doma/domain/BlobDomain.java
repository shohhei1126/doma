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
package org.seasar.doma.domain;

import java.sql.Blob;

import org.seasar.doma.DomaNullPointerException;

/**
 * {@link Blob} を値の型とするドメインの骨格実装です。
 * 
 * @author taedium
 * 
 * @param <D>
 *            ドメインの型
 */
public abstract class BlobDomain<D extends BlobDomain<D>>
        extends AbstractDomain<Blob, D> {

    /**
     * デフォルトの値でインスタンス化します。
     */
    protected BlobDomain() {
        this(null);
    }

    /**
     * 値を指定してインスタンス化します。
     * 
     * @param value
     *            値
     */
    protected BlobDomain(Blob value) {
        super(Blob.class, value);
    }

    @Override
    public <R, P, TH extends Throwable> R accept(
            DomainVisitor<R, P, TH> visitor, P p) throws TH {
        if (visitor == null) {
            throw new DomaNullPointerException("visitor");
        }
        if (BlobDomainVisitor.class.isInstance(visitor)) {
            @SuppressWarnings("unchecked")
            BlobDomainVisitor<R, P, TH> v = BlobDomainVisitor.class
                    .cast(visitor);
            return v.visitAbstractBlobDomain(this, p);
        }
        return visitor.visitUnknownDomain(this, p);
    }

}
