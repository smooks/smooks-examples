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
package org.smooks.examples.edifact2xml;

import org.smooks.Smooks;
import org.smooks.SmooksException;
import org.smooks.cartridges.edifact.EdifactReaderConfigurator;
import org.smooks.io.StreamUtils;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Main class that uses a Smooks XML configuration to configure the UN/EDIFACT
 * reader.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    protected static String runSmooksTransform() throws IOException, SAXException, SmooksException {

        // Configure Smooks using a Smooks config...
        //Smooks smooks = new Smooks("smooks-config.xml");
        
        // Or, configure Smooks programmatically...
        Smooks smooks = new Smooks();
        smooks.setReaderConfig(new EdifactReaderConfigurator("/d03b/EDIFACT-Messages.dfdl.xsd", Arrays.asList("PAXLST")));

        try {
            StringWriter writer = new StringWriter();

            smooks.filterSource(new StreamSource(new FileInputStream("PAXLST.edi")), new StreamResult(writer));

            return writer.toString();
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(readInputMessage());
        System.out.println("======================================\n");

        String messageOut = Main.runSmooksTransform();

        System.out.println("==============Message Out=============");
        System.out.println(messageOut);
        System.out.println("======================================\n\n");
    }

    private static String readInputMessage() throws IOException {
        return StreamUtils.readStreamAsString(new FileInputStream("PAXLST.edi"));
    }
}
