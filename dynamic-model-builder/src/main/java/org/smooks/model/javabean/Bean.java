/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Dynamic Model Builder
 * %%
 * Copyright (C) 2020 Smooks
 * %%
 * Licensed under the terms of the Apache License Version 2.0, or
 * the GNU Lesser General Public License version 3.0 or later.
 * 
 * SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
 * 
 * ======================================================================
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ======================================================================
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * =========================LICENSE_END==================================
 */
package org.smooks.model.javabean;


import org.smooks.cartridges.javabean.dynamic.serialize.DefaultNamespace;

import java.util.List;

/**
 * Bean configuration.
 * <p/>
 * Corresponds to the top level &lt;jb:bean&gt; element. 
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
@DefaultNamespace(uri = "http://www.milyn.org/xsd/smooks/javabean-1.3.xsd", prefix = "jb13")
public class Bean {

    private String beanId;
    private String beanClass;
    private String createOnElement;
    private String createOnElementNS;
    private List<Value> valueBindings;
    private List<Wiring> wireBindings;
    private List<Expression> expressionBindings;

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getCreateOnElement() {
        return createOnElement;
    }

    public void setCreateOnElement(String createOnElement) {
        this.createOnElement = createOnElement;
    }

    public String getCreateOnElementNS() {
        return createOnElementNS;
    }

    public void setCreateOnElementNS(String createOnElementNS) {
        this.createOnElementNS = createOnElementNS;
    }

    public List<Value> getValueBindings() {
        return valueBindings;
    }

    public void setValueBindings(List<Value> valueBindings) {
        this.valueBindings = valueBindings;
    }

    public List<Wiring> getWireBindings() {
        return wireBindings;
    }

    public void setWireBindings(List<Wiring> wireBindings) {
        this.wireBindings = wireBindings;
    }

    public List<Expression> getExpressionBindings() {
        return expressionBindings;
    }

    public void setExpressionBindings(List<Expression> expressionBindings) {
        this.expressionBindings = expressionBindings;
    }
}
