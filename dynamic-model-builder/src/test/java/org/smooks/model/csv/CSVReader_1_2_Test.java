/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Dynamic Model Builder
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
package org.smooks.model.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smooks.cartridges.javabean.dynamic.Model;
import org.smooks.cartridges.javabean.dynamic.ModelBuilder;
import org.smooks.model.core.SmooksModel;
import org.smooks.model.csv.CSVReader;
import org.smooks.model.csv.ListBinding;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class CSVReader_1_2_Test {

    private ModelBuilder modelBuilder;

    @BeforeEach
    public void setup() throws IOException, SAXException {
        modelBuilder = new ModelBuilder(SmooksModel.MODEL_DESCRIPTOR, false);
    }

    @Test
    public void test_01() throws IOException, SAXException {
        test("v12/csv-config-01.xml");
    }

    @Test
    public void test_02() throws IOException, SAXException {
        test("v12/csv-config-02.xml");
    }

    @Test
    public void test_03() throws IOException, SAXException {
        test("v12/csv-config-03.xml");
    }

    @Test
    public void test_04() throws IOException, SAXException {
        test("v12/csv-config-04.xml");
    }

    public void test(String messageFile) throws IOException, SAXException {
        Model<SmooksModel> model = modelBuilder.readModel(getClass().getResourceAsStream(messageFile), SmooksModel.class);

        StringWriter modelWriter = new StringWriter();
        model.writeModel(modelWriter);
//        System.out.println(modelWriter);
        assertFalse(DiffBuilder.compare(getClass().getResourceAsStream(messageFile)).ignoreWhitespace().withTest(modelWriter.toString()).build().hasDifferences());
    }

    @Test
    public void test_programmatic_build() throws IOException, SAXException {
        SmooksModel smooksModel = new SmooksModel();
        Model<SmooksModel> model = new Model<SmooksModel>(smooksModel, modelBuilder);
        CSVReader csvReader = new CSVReader();

        // Populate it...
        csvReader.setFields("name,address,age");
        csvReader.setRootElementName("people");
        csvReader.setRecordElementName("person");
        csvReader.setIndent(true);

        // Set strict on the model... should have no effect as it's not supported in v1.2...
        csvReader.setStrict(true);

        // Need to register all the "namespace root" bean instances...
        model.registerBean(csvReader).setNamespace("http://www.milyn.org/xsd/smooks/csv-1.2.xsd").setNamespacePrefix("csv12");

        // Add it in the appropriate place in the object graph....
        smooksModel.getReaders().add(csvReader);

        ListBinding listBinding = new ListBinding();
        listBinding.setBeanId("beanX");
        listBinding.setBeanClass("com.acme.XClass");

        // Add the ListBinding to the CSVReader, but no need to add it to the model since it is
        // not a "namespace root" object...
        csvReader.setListBinding(listBinding);

        StringWriter modelWriter = new StringWriter();
        model.writeModel(modelWriter);
//        System.out.println(modelWriter);
        assertFalse(DiffBuilder.compare(getClass().getResourceAsStream("v12/csv-config-03.xml")).
                checkForSimilar().
                withTest(modelWriter.toString()).build().hasDifferences());
    }
}
