/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: validation-basic
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
package org.smooks.examples.validation;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.rules.RuleEvalResult;
import org.smooks.cartridges.validation.OnFailResult;
import org.smooks.cartridges.validation.ValidationSink;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.io.source.StringSource;
import org.smooks.support.StreamUtils;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Simple example main class.
 *
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>
 */
public class Main {
    public static void main(final String... args) throws IOException, SAXException, SmooksException {
        final String messageIn = readInputMessage();

        // Uncomment to enable a Swedish locale.
        //Locale.setDefault(new Locale("sv", "SE"));

        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================");

        final ValidationSink sink = Main.runSmooks(messageIn);

        System.out.println("\n==============Validation Result=======");
        System.out.println("Errors:");
        for (OnFailResult result : sink.getErrors()) {
        	RuleEvalResult rule = result.getFailRuleResult();
            System.out.println("\t" + rule.getRuleName() + ": " + result.getMessage());
            System.out.println("\tSwedish:");
            System.out.println("\t" + result.getMessage(new Locale("sv", "SE")));
        }

        System.out.println("Warnings:");
        for (OnFailResult result : sink.getWarnings()) {
            System.out.println("\t" + result.getMessage());
            System.out.println("\tSwedish:");
            System.out.println("\t" + result.getMessage(new Locale("sv", "SE")));
        }

        System.out.println("======================================\n");
    }

    protected static ValidationSink runSmooks(final String messageIn) throws IOException, SAXException, SmooksException {
        // Instantiate Smooks with the config...
        final Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(Main.class.getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");

        try {
            // Create an exec context - no profiles....
            final ExecutionContext executionContext = smooks.createExecutionContext();
            final ValidationSink validationResult = new ValidationSink();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html", executionContext.getApplicationContext()));

            // Filter the input message...
            smooks.filterSource(executionContext, new StringSource(messageIn), validationResult);

            return validationResult;
        }
        finally {
            smooks.close();
        }
    }

    protected static String readInputMessage() {
        try {
            return StreamUtils.readStreamAsString(new FileInputStream("input-message.xml"), "UTF-8");
        } catch (final IOException e) {
            throw new RuntimeException("Error reading input message.", e);
        }
    }
}
