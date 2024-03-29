/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Java-Basic
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
package org.smooks.examples.javabasic;

import org.smooks.api.ExecutionContext;
import org.smooks.api.resource.visitor.dom.DOMElementVisitor;
import org.smooks.support.DomUtils;
import org.w3c.dom.Element;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Basic transformer that simply renames an element.
 * 
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class BasicJavaTransformer implements DOMElementVisitor {

    @Inject
    @Named("newName")
    private String newElementName;

    public void visitBefore(Element element, ExecutionContext executionContext) {
        // Not doing anything on this visit - wait untill after visiting the elements child content...
        }

    public void visitAfter(Element element, ExecutionContext executionContext) {
        // Just rename the target element - keeping child elements - not keeping attributes.
        DomUtils.renameElement(element, newElementName, true, false);
    }
}
