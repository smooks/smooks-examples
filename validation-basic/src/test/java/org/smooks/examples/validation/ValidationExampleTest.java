/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: validation-basic
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
package org.smooks.examples.validation;

import org.junit.jupiter.api.Test;
import org.smooks.cartridges.validation.OnFailResult;
import org.smooks.cartridges.validation.ValidationSink;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class ValidationExampleTest {

    @Test
    public void test() throws IOException, SAXException {
        ValidationSink sink = Main.runSmooks(Main.readInputMessage());
        List<OnFailResult> errors = sink.getErrors();
        List<OnFailResult> warnings = sink.getWarnings();

        assertEquals(3, errors.size());
        assertEquals(1, warnings.size());

        assertEquals("Invalid customer number 'user1' at '/Order/header/username'.  Customer number must begin with an uppercase character, followed by 5 digits.", errors.get(0).getMessage());
        assertEquals("Invalid product ID '364b' at '/Order/order-item/productId'.  Product ID must match pattern '[0-9]{3}'.", errors.get(1).getMessage());
        assertEquals("Order A188127 contains an order item for product 299 with a quantity of 2 and a unit price of 29.99. This exceeds the permitted per order item total.", errors.get(2).getMessage());

        assertEquals("Invalid email address 'harry.fletcher@gmail.' at '/Order/header/email'.  Email addresses match pattern '^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$'.", warnings.get(0).getMessage());
    }
}
