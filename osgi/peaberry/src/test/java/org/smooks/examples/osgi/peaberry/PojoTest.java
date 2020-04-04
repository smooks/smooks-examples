/*
	Milyn - Copyright (C) 2006 - 2010

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License (version 2.1) as published by the Free Software
	Foundation.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

	See the GNU Lesser General Public License for more details:
	http://www.gnu.org/licenses/lgpl.txt
*/
package org.smooks.examples.osgi.peaberry;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.smooks.Smooks;
import org.smooks.examples.osgi.peaberry.Pojo;
import org.xml.sax.SAXException;

import org.smooks.examples.osgi.peaberry.model.Order;
import org.smooks.examples.osgi.peaberry.model.OrderItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PojoTest {

    @Test
    public void test() throws IOException, SAXException {
        Smooks smooks = new Smooks("smooks-config.xml");
        Pojo pojo = new Pojo(smooks);
        Order order = pojo.filter("input-message.xml");

        assertNotNull(order);
        assertNotNull(order.getHeader());
        assertNotNull(order.getOrderItems());
        assertEquals(2, order.getOrderItems().size());

        assertEquals(1163616328000L, order.getHeader().getDate().getTime());
        assertEquals("Joe", order.getHeader().getCustomerName());
        assertEquals(new Long(123123), order.getHeader().getCustomerNumber());

        OrderItem orderItem = order.getOrderItems().get(0);
        assertEquals(111, orderItem.getProductId());
        assertEquals(new Integer(2), orderItem.getQuantity());

        orderItem = order.getOrderItems().get(1);
        assertEquals(222, orderItem.getProductId());
        assertEquals(new Integer(7), orderItem.getQuantity());
    }
}
