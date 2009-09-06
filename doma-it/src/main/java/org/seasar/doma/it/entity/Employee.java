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
package org.seasar.doma.it.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Version;
import org.seasar.doma.it.domain.HiredateDomain;
import org.seasar.doma.it.domain.IdDomain;
import org.seasar.doma.it.domain.NameDomain;
import org.seasar.doma.it.domain.NoDomain;
import org.seasar.doma.it.domain.SalaryDomain;
import org.seasar.doma.it.domain.VersionDomain;

@Entity
public interface Employee {

    @Id
    IdDomain employee_id();

    NoDomain employee_no();

    NameDomain employee_name();

    IdDomain manager_id();

    HiredateDomain hiredate();

    SalaryDomain salary();

    IdDomain department_id();

    IdDomain address_id();

    @Version
    VersionDomain version();
}
