/*-
 * ========================LICENSE_START=================================
 * Pipelines
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
package org.smooks.examples.pipeline;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.io.AbstractOutputStreamResource;
import org.smooks.io.FileUtils;
import org.smooks.io.payload.StringResult;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class Main {

    public static void main() throws IOException, SAXException {
        File file = new File("input-message.csv");
        ByteArrayOutputStream fakeHttpInventoryOutputStream = new ByteArrayOutputStream();

        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(FileUtils.readFile(file)));
        System.out.println("======================================\n");

        String messageOut;
        try (InputStream fakeFtpCsvInputStream = new FileInputStream(file)) {
            messageOut = filterSource(fakeFtpCsvInputStream, fakeHttpInventoryOutputStream);
        }
        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }

    public static String filterSource(InputStream ftpInputStream, OutputStream inventoryOutputStream) throws IOException, SAXException {
        AbstractOutputStreamResource abstractOutputStreamResource = new AbstractOutputStreamResource() {
            @Override
            public OutputStream getOutputStream(ExecutionContext executionContext) {
                return inventoryOutputStream;
            }
        };
        abstractOutputStreamResource.setResourceName("inventoryOutputStream");

        Smooks smooks = new Smooks("smooks-config.xml");
        StringResult stringResult = new StringResult();
        try {
            smooks.addVisitor(abstractOutputStreamResource, "#document");
            smooks.filterSource(new StreamSource(ftpInputStream), stringResult);
        } finally {
            smooks.close();
        }

        return stringResult.toString();
    }
}