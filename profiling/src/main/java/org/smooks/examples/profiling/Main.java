/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Profiling
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
package org.smooks.examples.profiling;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.io.sink.WriterSink;
import org.smooks.io.source.StreamSource;
import org.smooks.support.StreamUtils;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example main class.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    private static byte[] messageIn = readInputMessage();
    private Smooks smooks = null;

    protected Main() throws IOException, SAXException {
        // Instantiate Smooks with the config...
        smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");
    }

    protected String runSmooksTransform(String targetProfile) throws IOException {
        try {
            // Create an exec context for the target profile....
            ExecutionContext executionContext = smooks.createExecutionContext(targetProfile);
            CharArrayWriter outputWriter = new CharArrayWriter();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html", executionContext.getApplicationContext()));

            // Filter the input message to the outputWriter, using the execution context...
            smooks.filterSource(executionContext, new StreamSource<>(new ByteArrayInputStream(messageIn)), new WriterSink<>(outputWriter));

            return outputWriter.toString();
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

        Main smooksMain = new Main();
        transformForProfile("message-exchange-1", smooksMain);
        transformForProfile("message-exchange-2", smooksMain);
        transformForProfile("message-exchange-3", smooksMain);
        transformForProfile("message-exchange-4", smooksMain);
        transformForProfile("message-exchange-5", smooksMain);
    }

    private static void transformForProfile(String targetProfile, Main smooksMain) throws IOException {
        readCommandPrompt(targetProfile);
        System.out.println("\n");
        System.out.println(smooksMain.runSmooksTransform(targetProfile));
        System.out.println("\n");
    }

    private static void readCommandPrompt(String targetProfile) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> Press 'enter' to see the transform for target profile '" + targetProfile + "':");
            in.readLine();
        } catch (IOException e) {
        }
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>".getBytes();
        }
    }
}
