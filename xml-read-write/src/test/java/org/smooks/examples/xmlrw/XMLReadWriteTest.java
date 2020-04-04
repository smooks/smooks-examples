/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.smooks.examples.xmlrw;

import org.smooks.examples.xmlrw.model.Order;
import org.smooks.examples.xmlrw.model.OrderItem;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.smooks.cartridges.javabean.binding.xml.XMLBinding;
import org.smooks.payload.StringSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class XMLReadWriteTest {

	@Test
    public void test() throws IOException, SAXException {
        XMLBinding xmlBinding = new XMLBinding().add("smooks-config.xml");
        xmlBinding.intiailize();

        Order order = xmlBinding.fromXML(new StringSource(Main.orderXMLMessage), Order.class);

        assertNotNull(order);
        assertNotNull(order.getHeader());
        assertNotNull(order.getOrderItems());
        assertEquals(2, order.getOrderItems().size());

        assertEquals(1163616328000L, order.getHeader().getDate().getTime());
        assertEquals("Joe", order.getHeader().getCustomerName());
        assertEquals(new Long(123123), order.getHeader().getCustomerNumber());

        OrderItem orderItem = order.getOrderItems().get(0);
        assertEquals(8.90d, orderItem.getPrice(), 0d);
        assertEquals(111, orderItem.getProductId());
        assertEquals(new Integer(2), orderItem.getQuantity());

        orderItem = order.getOrderItems().get(1);
        assertEquals(5.20d, orderItem.getPrice(), 0d);
        assertEquals(222, orderItem.getProductId());
        assertEquals(new Integer(7), orderItem.getQuantity());

        StringWriter orderWriter = new StringWriter();
        xmlBinding.toXML(order, orderWriter);

        assertFalse(DiffBuilder.compare(Main.orderXMLMessage).ignoreWhitespace().withTest(orderWriter.toString()).build().hasDifferences());
    }
}
