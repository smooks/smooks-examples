/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License (version 2.1) as published by the Free Software
 *  Foundation.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *  See the GNU Lesser General Public License for more details:
 *  http://www.gnu.org/licenses/lgpl.txt
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
public class CSVReader_1_3_Test {

    private ModelBuilder modelBuilder;

    @BeforeEach
    public void setUp() throws IOException, SAXException {
        modelBuilder = new ModelBuilder(SmooksModel.MODEL_DESCRIPTOR, false);
    }

    @Test
    public void test_01() throws IOException, SAXException {
        test("v13/csv-config-01.xml");
    }

    @Test
    public void test_02() throws IOException, SAXException {
        test("v13/csv-config-02.xml");
    }

    @Test
    public void test_03() throws IOException, SAXException {
        test("v13/csv-config-03.xml");
    }

    @Test
    public void test_04() throws IOException, SAXException {
        test("v13/csv-config-04.xml");
    }

    @Test
    public void test_05() throws IOException, SAXException {
        test("v13/csv-config-05.xml");
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

        // Need to register all the "namespace root" bean instances...
        model.registerBean(csvReader);

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
        assertFalse(DiffBuilder.compare(getClass().getResourceAsStream("v13/csv-config-03.xml")).
                checkForSimilar().
                withTest(modelWriter.toString()).build().hasDifferences());
    }
}