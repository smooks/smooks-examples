/*-
 * ========================LICENSE_START=================================
 * Cross Domain Solution
 * %%
 * Copyright (C) 2020 - 2021 Smooks
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
package org.smooks.examples.cds;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.io.Sink;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.io.AbstractOutputStreamResource;
import org.smooks.io.sink.StreamSink;
import org.smooks.io.sink.StringSink;
import org.smooks.io.source.StreamSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String... args) throws IOException, SAXException {
        FileOutputStream badResultOutputStream = new FileOutputStream("bad-result.xml");
        File result = new File("good-result.ntf");
        Smooks smooks = createSmooks(badResultOutputStream);

        System.out.println("\n============== Filtering 'i_3001a.good.ntf' ==============\n");
        try (InputStream inputStream = new FileInputStream("i_3001a.good.ntf")) {
            filterSource(smooks, inputStream, new StreamSink<>(new FileOutputStream(result)));
        }
        System.out.println("============== Saved valid result to 'good-result.ntf' ==============\n");

        System.out.println("============== Filtering 'i_3001a.bad.ntf' ==============");
        try (InputStream inputStream = new FileInputStream("i_3001a.bad.ntf")) {
            filterSource(smooks, inputStream, new StringSink());
        }
        System.out.println("============== Saved invalid result to 'bad-result.xml' ==============\n");

        badResultOutputStream.close();
    }

    protected static Smooks createSmooks(OutputStream deadLetterOutputStream) throws IOException, SAXException {
        AbstractOutputStreamResource deadLetterOutputStreamResource = new AbstractOutputStreamResource() {
            @Override
            public OutputStream getOutputStream(ExecutionContext executionContext) {
                return deadLetterOutputStream;
            }

            @Override
            protected void closeResource(ExecutionContext executionContext) {
            }
        };
        deadLetterOutputStreamResource.setResourceName("deadLetterStream");

        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(Main.class.getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");
        smooks.addVisitor(deadLetterOutputStreamResource, "#document");

        return smooks;
    }

    protected static void filterSource(Smooks smooks, InputStream inputStream, Sink sink) throws IOException {
        ExecutionContext executionContext = smooks.createExecutionContext();
        HtmlReportGenerator htmlReportGenerator = new HtmlReportGenerator("target/report/report.html", executionContext.getApplicationContext());
        htmlReportGenerator.getReportConfiguration().setAutoCloseWriter(false);
        executionContext.getContentDeliveryRuntime().addExecutionEventListener(htmlReportGenerator);
        executionContext.setContentEncoding("ISO-8859-1");
        smooks.filterSource(executionContext, new StreamSource<>(inputStream), sink);
    }
}
