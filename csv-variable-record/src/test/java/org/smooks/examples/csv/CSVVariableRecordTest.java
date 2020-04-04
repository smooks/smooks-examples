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
package org.smooks.examples.csv;

import java.io.IOException;
import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;
import org.smooks.examples.csv.Main;
import org.xml.sax.SAXException;
import org.smooks.io.StreamUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class CSVVariableRecordTest {

	@Test
    public void test() throws IOException, SAXException {
        byte[] expected = StreamUtils.readStream(getClass().getResourceAsStream("/expected.xml"));
        String result = Main.runSmooksTransform();

        StringBuffer s1 = StreamUtils.trimLines(new ByteArrayInputStream(expected));
        StringBuffer s2 = StreamUtils.trimLines(new ByteArrayInputStream(result.getBytes()));

        assertEquals(s1.toString(), s2.toString(), "Expected:\n" + s1 + "\nActual:\n" + s2);
    }
}
