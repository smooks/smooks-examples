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

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.smooks.Smooks;
import org.smooks.io.sink.ByteSink;
import org.smooks.support.StreamUtils;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainTestCase {

    private static Smooks smooks;
    private static ByteArrayOutputStream deadLetterOutputStream;

    @BeforeAll
    public static void beforeAll() throws IOException, SAXException {
        deadLetterOutputStream = new ByteArrayOutputStream();
        smooks = Main.createSmooks(deadLetterOutputStream);
    }

    @AfterAll
    public static void afterAll() {
        smooks.close();
    }

	@Test
    public void testFilterSourceGivenValidFile() throws IOException {
        File inputFile = new File("i_3001a.good.ntf");
        ByteSink byteSink = new ByteSink();
        Main.filterSource(smooks, Files.newInputStream(inputFile.toPath()), byteSink);
        assertArrayEquals(FileUtils.readFileToByteArray(inputFile), byteSink.getResult());
    }

    @Test
    public void testFilterSourceGivenInvalidFile() throws IOException {
        ByteSink byteSink = new ByteSink();
        Main.filterSource(smooks, new FileInputStream("i_3001a.bad.ntf"), byteSink);
        assertEquals(0, byteSink.getResult().length);
        assertFalse(DiffBuilder.compare(StreamUtils.readStreamAsString(Thread.currentThread().getContextClassLoader().getResourceAsStream("expected.xml"), "UTF-8"))
                .ignoreWhitespace()
                .withTest(new String(deadLetterOutputStream.toByteArray(), StandardCharsets.UTF_8)).build().hasDifferences());
    }
}
