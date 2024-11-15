/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: File Router (Splitter)
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
package org.smooks.examples.filerouter.splitter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smooks.FilterSettings;
import org.smooks.Smooks;
import org.smooks.StreamFilterType;
import org.smooks.api.ExecutionContext;
import org.smooks.io.source.StreamSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class ToFileRoutingTest {

    private File targetDir = new File("target/orders");
    private File file1 = new File(targetDir, "order-231-1.xml");
    private File file2 = new File(targetDir, "order-231-2.xml");
    private File file3 = new File(targetDir, "order-231-3.xml");
    private File file4 = new File(targetDir, "order-231-4.xml");
    private File file5 = new File(targetDir, "order-231-5.xml");
    private File file6 = new File(targetDir, "order-231-6.xml");
    private File file7 = new File(targetDir, "order-231-7.xml");
    private File listFile = new File(targetDir, "order-231.lst");

    @BeforeEach
    public void setUp() throws Exception {
        deleteFiles();
    }

    @AfterEach
    public void tearDown() throws Exception {
        deleteFiles();
    }

    private void deleteFiles() {
        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
        file5.delete();
        file6.delete();
        file7.delete();
        listFile.delete();
    }

    @Test
    public void test_dom() throws IOException, SAXException {
        test(StreamFilterType.DOM);
    }

    @Test
    public void test_sax() throws IOException, SAXException {
        test(StreamFilterType.SAX_NG);
    }

    public void test(StreamFilterType filterType) throws IOException, SAXException {
        startSmooksThread(filterType);

        // The highWaterMark is set to 3 in the smooks config...
        waitForFile(file1, 5000);
        waitForFile(file2, 5000);
        waitForFile(file3, 5000);

        sleep(500);
        // file4 shouldn't be there...
        assertTrue(!file4.exists(), "file4 exists!");

        // delete file1 and file4 should appear then...
        file1.delete();
        waitForFile(file4, 5000);
        // file4 should be there...
        assertTrue(file4.exists(), "file4 doesn't exists!");

        sleep(1000);
        // file5 shouldn't be there...
        assertTrue(!file5.exists(), "file5 exists!");

        // delete file2, file3, file4 and file5, file6 and file7 should appear then...
        file2.delete();
        file3.delete();
        file4.delete();
        sleep(2000);
        waitForFile(file7, 5000);
        assertTrue(file5.exists());
        assertTrue(file6.exists());
        assertTrue(file7.exists());
        assertTrue(listFile.exists());
    }

    private void startSmooksThread(StreamFilterType filterType) {
        SmooksThread thread = new SmooksThread(filterType);

        thread.start();
        while(!thread.running) {
            sleep(100);
        }
    }

    private void waitForFile(File file, int maxWait) {
        long start = Math.max(500, System.currentTimeMillis());

        while(!file.exists() && (System.currentTimeMillis() < start + maxWait)) {
            sleep(100);
        }
    }

    private void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

    private class SmooksThread extends Thread {
        boolean running = false;
        private StreamFilterType filterType;

        public SmooksThread(StreamFilterType filterType) {
            this.filterType = filterType;
        }

        public void run() {
            Smooks smooks = null;

            try {
                try {
                    smooks = new Smooks(new FileInputStream("smooks-config.xml"));
                } catch (IOException e) {
                    fail(e.getMessage());
                } catch (SAXException e) {
                    fail(e.getMessage());
                }

                ExecutionContext execCtx = smooks.createExecutionContext();
                //execCtx.setEventListener(new HtmlReportGenerator("/zap/x.html"));
                smooks.setFilterSettings(new FilterSettings(filterType));
                running = true;
                smooks.filterSource(execCtx, new StreamSource<>(getClass().getResourceAsStream("/order-message.xml")), null);
            } finally {
                smooks.close();
            }
        }
    }
}
