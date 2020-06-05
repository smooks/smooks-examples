/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: File Router (Consumer)
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
package org.smooks.examples.filerouter.consumer;

import org.smooks.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple file consumer.
 * <p/>
 * Eats the files produced by the Smooks file splitter.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        File fileDir = new File("../splitter-router/target/orders");
        SplitFilenameFilter filter = new SplitFilenameFilter();

        fileDir.mkdirs();
        (new File(fileDir, "order-332.lst")).delete();
        (new File(fileDir, "order-332.lst")).deleteOnExit();

        System.out.println("Started!");
        System.out.println("Waiting...\n");
        while (true) {
            File[] files = fileDir.listFiles(filter);

            if (files.length > 0) {
                for (File file : files) {
                    if (file.getName().endsWith(".xml")) {
                        System.out.println("Consuming File: " + file.getName());
                        System.out.println(new String(FileUtils.readFile(file)));
                        System.out.println("\n");
                        file.delete();
                    }

                    Thread.sleep(500);
                }
                System.out.println("Waiting...");
            }

            Thread.sleep(1000);
        }
    }

    public static class SplitFilenameFilter implements FileFilter {

        private Pattern regexPattern = Pattern.compile("order-.*.xml");

        public boolean accept(File file) {
            Matcher matcher = regexPattern.matcher(file.getName());
            return matcher.matches();
        }
    }
}
