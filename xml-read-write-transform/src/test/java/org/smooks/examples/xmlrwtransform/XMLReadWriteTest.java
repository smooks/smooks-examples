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

package org.smooks.examples.xmlrwtransform;

import org.smooks.examples.xmlrwtransform.model.Order;
import org.junit.jupiter.api.Test;
import org.smooks.cartridges.javabean.binding.xml.XMLBinding;
import org.smooks.payload.StringSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class XMLReadWriteTest {

    /**
     * Read using v1 binding config and write back out using v2 binding config... i.e. transform from v1 to v2...
     */
    @Test
    public void test() throws IOException, SAXException {
        XMLBinding xmlV1Binding = new XMLBinding().add("v1-binding-config.xml");
        XMLBinding xmlV2Binding = new XMLBinding().add("v2-binding-config.xml");

        xmlV1Binding.intiailize();
        xmlV2Binding.intiailize();

        Order order = xmlV1Binding.fromXML(new StringSource(Main.orderV1XMLMessage), Order.class);

        StringWriter orderWriter = new StringWriter();
        xmlV2Binding.toXML(order, orderWriter);

        assertFalse(DiffBuilder.compare(Main.orderV2XMLMessage).ignoreComments().ignoreWhitespace().withTest(orderWriter.toString()).build().hasDifferences());
    }
}
