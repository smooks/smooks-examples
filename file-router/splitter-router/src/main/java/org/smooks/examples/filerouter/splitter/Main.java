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

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.routing.file.FileListAccessor;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.io.sink.StreamSink;
import org.smooks.io.source.StreamSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This is a simple example that demonstrates how Smooks can be 
 * configured to "route" the output of a transform to file(s).
 * 
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>
 *
 */
public class Main {
    private static final String LINE_SEP = System.getProperty("line.separator");

    protected void runSmooksTransform() throws IOException, SAXException, ClassNotFoundException {
        final Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");

        try {
            final ExecutionContext executionContext = smooks.createExecutionContext();

            //	create the source and result
            final StreamSource<FileInputStream> source = new StreamSource<>(new FileInputStream("target/input-message.xml"));
            final StreamSink<?> sink = null;

            //executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report.html"));

            //	perform the transform
            smooks.filterSource(executionContext, source, sink);

            //	display the output from the transform
            System.out.println(LINE_SEP);
            System.out.println("List file : [" + FileListAccessor.getListFileNames(executionContext) + "]");

            //	uncomment to print the files
            printFiles(executionContext);
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException, InterruptedException, ClassNotFoundException {
        String fileName = "target/input-message.xml";
        System.out.println(fileName);
        System.out.println();
        String nrofLineItems = pause("Please specify number of order-items to generate in the input message > ");
        InputOrderGenerator.main(new String[]{fileName, nrofLineItems});

        final Main smooksMain = new Main();

        System.out.println(LINE_SEP);
        System.out.println("input-message.xml needs to be transformed and appended to a file");
        System.out.println(LINE_SEP);
        pause("Press 'enter' to display the transformed message...");
        smooksMain.runSmooksTransform();
        System.out.println(LINE_SEP);
        pause("That's it ");
    }

    /*
     * Can be used to print the list of files and their contents.
     * Beware that this can cause memory issues as the whole list file will be
     * read into memory. This method should only be used with smaller transforms.
     */
    @SuppressWarnings("unused")
    private void printFiles(ExecutionContext executionContext) throws IOException, ClassNotFoundException {
        List<String> allListFiles = FileListAccessor.getListFileNames(executionContext);
        for (String listFile : allListFiles) {
            List<String> fileNames = (List<String>) FileListAccessor.getFileList(executionContext, listFile);
            System.out.println("Contains [" + fileNames.size() + "] files");
            for (String fileName : fileNames) {
                System.out.println("fileName :  [" + fileName + "]");
            }
        }
    }

    private static String pause(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> " + message);
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(LINE_SEP);
        return null;
    }

}
