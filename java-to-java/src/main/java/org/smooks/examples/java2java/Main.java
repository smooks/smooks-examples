/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Java-to-Java
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
package org.smooks.examples.java2java;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.examples.java2java.srcmodel.Order;
import org.smooks.examples.java2java.trgmodel.LineOrder;
import org.smooks.io.sink.JavaSink;
import org.smooks.io.source.JavaSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example main class.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    /**
     * Run the transform for the request or response.
     * @param srcOrder The input Java Object.
     * @return The transformed Java Object XML.
     */
    protected LineOrder runSmooksTransform(Order srcOrder) throws IOException, SAXException {
        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();

            // Transform the source Order to the target LineOrder via a
            // JavaSource and JavaResult instance...
            JavaSource source = new JavaSource(srcOrder);
            JavaSink sink = new JavaSink();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html", executionContext.getApplicationContext()));

            smooks.filterSource(executionContext, source, sink);

            return (LineOrder) sink.getBean("lineOrder");
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        Main smooksMain = new Main();
        Order order = new Order();
        LineOrder lineOrder;

        pause("Press 'enter' to display the input Java Order message...");
        System.out.println("\n");
        System.out.println(order);
        System.out.println("\n\n");

        System.out.println("This needs to be transformed to another Java Object.");
        pause("Press 'enter' to display the transformed Java Object...");
        lineOrder = smooksMain.runSmooksTransform(order);
        System.out.println("\n");
        System.out.println(lineOrder);
        System.out.println("\n\n");

        pause("And that's it!");
        System.out.println("\n\n");
    }

    private static void pause(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> " + message);
            in.readLine();
        } catch (IOException e) {
        }
        System.out.println("\n");
    }
}
