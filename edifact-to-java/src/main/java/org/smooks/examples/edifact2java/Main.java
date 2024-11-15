/*-
 * ========================LICENSE_START=================================
 * EDIFACT to Java
 * %%
 * Copyright (C) 2020 - 2023 Smooks
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
package org.smooks.examples.edifact2java;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.smooks.Smooks;
import org.smooks.api.SmooksException;
import org.smooks.edifact.binding.d03b.Interchange;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.io.sink.WriterSink;
import org.smooks.io.source.StreamSource;
import org.smooks.support.StreamUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Main class that uses a Smooks XML configuration to configure the UN/EDIFACT
 * reader.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    protected static Interchange runSmooksTransform() throws IOException, SAXException, SmooksException, JAXBException {
        // Configure Smooks using a Smooks config...
        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(Main.class.getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");

        try {
            final StringWriter writer = new StringWriter();
            smooks.filterSource(new StreamSource<>(Main.class.getResourceAsStream("/PAXLST.edi")), new WriterSink<>(writer));

            JAXBContext jaxbContext = JAXBContext.newInstance(Interchange.class, org.smooks.edifact.binding.service.ObjectFactory.class, org.smooks.edifact.binding.d03b.ObjectFactory.class);
            return  (Interchange) jaxbContext.createUnmarshaller().unmarshal(new javax.xml.transform.stream.StreamSource(new StringReader(writer.toString())));
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException, JAXBException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(readInputMessage());
        System.out.println("======================================\n");

        Interchange messageOut = Main.runSmooksTransform();

        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }

    private static String readInputMessage() throws IOException {
        return StreamUtils.readStreamAsString(Main.class.getResourceAsStream("/PAXLST.edi"), "UTF-8");
    }
}
