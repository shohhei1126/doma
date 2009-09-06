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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.seasar.doma.DomaNullPointerException;

/**
 * {@link ArrayList} を値の型とするドメインのための骨格実装です。
 * 
 * @author taedium
 * 
 * @param <E>
 *            要素の型
 * @param <D>
 *            ドメインの型
 */
public abstract class ArrayListDomain<E, D extends ArrayListDomain<E, D>>
        extends AbstractDomain<ArrayList<E>, ArrayListDomain<E, D>>
        implements
        SerializableDomain<ArrayList<E>, ArrayListDomain<E, D>> {

    private static final long serialVersionUID = 1L;

    /**
     * デフォルトの値でインスタンス化します。
     */
    public ArrayListDomain() {
        this(null);
    }

    /**
     * 値を指定してインスタンス化します。
     * 
     * @param value
     *            値
     */
    @SuppressWarnings("unchecked")
    public ArrayListDomain(ArrayList<E> value) {
        super((Class<ArrayList<E>>) new ArrayList<E>().getClass(), value);
    }

    @Override
    public <R, P, TH extends Throwable> R accept(
            DomainVisitor<R, P, TH> visitor, P p) throws TH {
        if (visitor == null) {
            throw new DomaNullPointerException("visitor");
        }
        if (ArrayListDomainVisitor.class.isInstance(visitor)) {
            @SuppressWarnings("unchecked")
            ArrayListDomainVisitor<R, P, TH> v = ArrayListDomainVisitor.class
                    .cast(visitor);
            return v.visitAbstractArrayListDomain(this, p);
        }
        return visitor.visitUnknownDomain(this, p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        ArrayListDomain<?, ?> other = ArrayListDomain.class
                .cast(o);
        if (value == null) {
            return other.value == null;
        }
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : null;
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream inputStream) throws IOException,
            ClassNotFoundException {
        inputStream.defaultReadObject();
        valueClass = (Class<ArrayList<E>>) inputStream.readObject();
        value = (ArrayList<E>) inputStream.readObject();
        changed = inputStream.readBoolean();
    }

    private void writeObject(ObjectOutputStream outputStream)
            throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(valueClass);
        outputStream.writeObject(value);
        outputStream.writeBoolean(changed);
    }
}
