/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: YAML-to-Java
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
package org.smooks.examples.yaml2java;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.examples.yaml2java.model.Order;
import org.smooks.io.sink.StringSink;
import org.smooks.io.source.StreamSource;
import org.smooks.support.FileUtils;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example main class.
 *
 * @author <a href="mailto:maurice@zeijen.net">maurice@zeijen.net</a>
 */
public class Main {

    private static byte[] messageIn = readInputMessage();

    private final Smooks smooks;

    protected Main() throws IOException, SAXException {
        // Instantiate Smooks with the config...
        smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");
    }

    protected String runSmooksTransform(ExecutionContext executionContext) throws IOException, SAXException, SmooksException {
        try {
            StringSink sink = new StringSink();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html", smooks.getApplicationContext()));

            // Filter the input message to the outputWriter, using the execution context...
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), sink);

            return sink.toString();
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

        pause("The YAML input stream can be seen above.  Press 'enter' to see how this stream is transformed into DOM representation...");

        Main smooksMain = new Main();
        ExecutionContext executionContext = smooksMain.smooks.createExecutionContext();
        System.out.println("==============YAML as XML=============");
        System.out.println(smooksMain.runSmooksTransform(executionContext));
        System.out.println("======================================\n\n");

        pause("Now press 'enter' to see how this XML loads into the Order Object graph...");

        System.out.println("==============YAML as Java Object Graph=============");
        System.out.println(executionContext.getBeanContext().getBean(Order.class));
        System.out.println("======================================\n\n");

        pause("And that's it!  Press 'enter' to finish...");
    }

    private static byte[] readInputMessage() {
        try {
            return FileUtils.readFile(new File("input-message.yaml"));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>".getBytes();
        }
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

    public String runSmooksTransform() throws IOException, SAXException {
        ExecutionContext executionContext = smooks.createExecutionContext();
        return runSmooksTransform(executionContext);
    }
}
