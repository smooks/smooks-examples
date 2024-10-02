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

import org.smooks.Smooks;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.javabean.binding.xml.XMLBinding;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.examples.xmlrw.model.Order;
import org.smooks.examples.xmlrw.model.OrderItem;
import org.smooks.io.source.StringSource;
import org.smooks.support.StreamUtils;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example of the XMLBinding utility.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    public static String orderXMLMessage = readInputMessage();

    public static void main(String[] args) throws IOException, SAXException, SmooksException {

        // Create and initilise the XMLBinding instance...
        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(Main.class.getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");
        XMLBinding xmlBinding = new XMLBinding(smooks);
        xmlBinding.initialise();

        // Read the order XML into the Order Object model...
        Order order = xmlBinding.fromXML(new StringSource(orderXMLMessage), Order.class);

        // Do something with the order....

        // Write the Order object model instance back out to XML...
        String outXML = xmlBinding.toXML(order);  // (Note: There's also a version of toXML() that takes a Writer)

        // Display read/write info to the example user...
        displayInputMessage(orderXMLMessage);
        displayReadJavaObjects(order);
        displayWriteXML(outXML);
    }

    private static void displayInputMessage(String orderXMLMessage) {
        userMessage("\n\n** Press enter to see the Order input sample message (input-message.xml):");
        System.out.println("\n\n");
        System.out.println("==============Source Order XML Message==============");
        System.out.println(orderXMLMessage);
        System.out.println("====================================================");
    }

    private static void displayReadJavaObjects(Order order) {
        userMessage("\n\n** Press enter to see the Order message as bound into the Order object model (XML reading):");
        System.out.println("============Order Java Bean===========");
        System.out.println("Header - Customer Name: " + order.getHeader().getCustomerName());
        System.out.println("       - Customer Num:  " + order.getHeader().getCustomerNumber());
        System.out.println("       - Order Date:    " + order.getHeader().getDate());
        System.out.println("\n");
        System.out.println("Order Items:");
        for(int i = 0; i < order.getOrderItems().size(); i++) {
            OrderItem orderItem = order.getOrderItems().get(i);
            System.out.println("       (" + (i + 1) + ") Product ID:  " + orderItem.getProductId());
            System.out.println("       (" + (i + 1) + ") Quantity:    " + orderItem.getQuantity());
            System.out.println("       (" + (i + 1) + ") Price:       " + orderItem.getPrice());
        }
        System.out.println("======================================");
    }

    private static void displayWriteXML(String xml) {
        userMessage("\n\n** Press enter to see the Order object model serialized back out to XML  (XML writing):");
        System.out.println("==============Serialized Order Bean XML==============");
        System.out.println(xml);
        System.out.println("=====================================================\n");
    }

    private static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream("input-message.xml"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>";
        }
    }

    private static void userMessage(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> " + message);
            in.readLine();
        } catch (IOException e) {
        }
        System.out.println("\n");
    }
}
