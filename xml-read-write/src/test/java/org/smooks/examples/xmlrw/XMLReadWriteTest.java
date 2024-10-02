/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: XML Read/Write
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
package org.smooks.examples.xmlrw;

import org.junit.jupiter.api.Test;
import org.smooks.cartridges.javabean.binding.xml.XMLBinding;
import org.smooks.examples.xmlrw.model.Order;
import org.smooks.examples.xmlrw.model.OrderItem;
import org.smooks.io.source.StringSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class XMLReadWriteTest {

	@Test
    public void test() throws IOException, SAXException {
        XMLBinding xmlBinding = new XMLBinding().add("smooks-config.xml");
        xmlBinding.initialise();

        Order order = xmlBinding.fromXML(new StringSource(Main.orderXMLMessage), Order.class);

        assertNotNull(order);
        assertNotNull(order.getHeader());
        assertNotNull(order.getOrderItems());
        assertEquals(2, order.getOrderItems().size());

        assertEquals(1163616328000L, order.getHeader().getDate().getTime());
        assertEquals("Joe", order.getHeader().getCustomerName());
        assertEquals(Long.valueOf(123123), order.getHeader().getCustomerNumber());

        OrderItem orderItem = order.getOrderItems().get(0);
        assertEquals(8.90d, orderItem.getPrice(), 0d);
        assertEquals(111, orderItem.getProductId());
        assertEquals(Integer.valueOf(2), orderItem.getQuantity());

        orderItem = order.getOrderItems().get(1);
        assertEquals(5.20d, orderItem.getPrice(), 0d);
        assertEquals(222, orderItem.getProductId());
        assertEquals(Integer.valueOf(7), orderItem.getQuantity());

        StringWriter orderWriter = new StringWriter();
        xmlBinding.toXML(order, orderWriter);

        assertFalse(DiffBuilder.compare(Main.orderXMLMessage).ignoreWhitespace().withTest(orderWriter.toString()).build().hasDifferences());
    }
}
