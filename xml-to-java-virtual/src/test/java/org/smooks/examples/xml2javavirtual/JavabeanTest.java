/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: XML-to-Java-Virtual
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
package org.smooks.examples.xml2javavirtual;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class JavabeanTest {

	@Test
    public void test() throws IOException, SAXException {
        Map order = Main.runSmooks();

        assertNotNull(order);
        assertNotNull(order.get("header"));
        assertNotNull(order.get("orderItems"));
        assertEquals(2, ((List)order.get("orderItems")).size());

        assertEquals(1163616328000L, ((Date)((Map)order.get("header")).get("date")).getTime());
        assertEquals("Joe", ((Map)order.get("header")).get("customerName"));
        assertEquals(new Long(123123), ((Map)order.get("header")).get("customerNumber"));

        Map orderItem = (Map) ((List)order.get("orderItems")).get(0);
        assertEquals(8.90d, orderItem.get("price"));
        assertEquals(111L, orderItem.get("productId"));
        assertEquals(new Integer(2), orderItem.get("quantity"));

        orderItem = (Map) ((List)order.get("orderItems")).get(1);
        assertEquals(5.20d, orderItem.get("price"));
        assertEquals(222L, orderItem.get("productId"));
        assertEquals(new Integer(7), orderItem.get("quantity"));
    }
}
