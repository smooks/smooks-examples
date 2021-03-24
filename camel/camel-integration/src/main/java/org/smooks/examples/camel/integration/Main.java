/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Smooks Camel Integration
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
package org.smooks.examples.camel.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.smooks.support.StreamUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example main class.
 *
 * @author Daniel Bevenius
 */
public class Main {
    public static void main(String... args) throws Exception {
        printStartMessage();
        CamelContext camelContext = configureAndStartCamel(getDSLType(args));
        camelContext.stop();
        printEndMessage();
    }

    private static String getDSLType(String... args) {
        if (args.length > 0)
            return args[0];
        else
            return "JavaDSL";
    }

    private static String readInputMessage() {
        try {
            byte[] bytes = StreamUtils.readStream(new FileInputStream("input-message.xml"));
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>";
        }
    }

    private static void printStartMessage() {
        String payload = readInputMessage();
        System.out.println("\n\n==============Message In==============");
        System.out.println(payload);
        System.out.println("======================================\n");
        pause("The example xml can be seen above.  Press 'enter' have this");
    }

    private static void pause(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> " + message);
            in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
    }

    private static CamelContext configureAndStartCamel(String type) throws Exception {
        CamelContext camelContext;
        if ("SpringDSL".equals(type)) {
            ApplicationContext springContext = new ClassPathXmlApplicationContext("camel-context-test.xml");
            camelContext = (CamelContext) springContext.getBean("camelContext");
        } else {
            camelContext = new DefaultCamelContext();
            camelContext.addComponent("jms", camelContext.getComponent("mock"));
            camelContext.addRoutes(new ExampleRouteBuilder());
            camelContext.start();
        }

        Thread.sleep(3000);
        return camelContext;
    }

    private static void printEndMessage() {
        System.out.println("\n\n");
        pause("And that's it!  Press 'enter' to finish...");
    }

}
