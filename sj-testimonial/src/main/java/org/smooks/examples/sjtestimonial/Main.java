/*-
 * ========================LICENSE_START=================================
 * Smooks Testimonials :: SJ Rollingstock
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
package org.smooks.examples.sjtestimonial;

import org.smooks.Smooks;
import org.smooks.SmooksException;
import org.smooks.payload.JavaResult;
import org.smooks.event.report.HtmlReportGenerator;
import org.smooks.container.ExecutionContext;
import org.smooks.io.StreamUtils;
import org.xml.sax.SAXException;
import se.sj.ipl.rollingstock.domain.RollingStockList;
import se.sj.ipl.rollingstock.domain.Rollingstock;
import se.sj.ipl.rollingstock.domain.Vehicle;

import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Simple example main class.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    public static byte[] messageIn = readInputMessage();

    protected static Map runSmooksTransform(String config) throws IOException, SAXException, SmooksException {
        // Instantiate Smooks with the config...
        Smooks smooks = new Smooks(config);

        try {
             // Create an exec context - no profiles....
            ExecutionContext executionContext = smooks.createExecutionContext();
            // The result of this transform is a set of Java objects...
            JavaResult result = new JavaResult();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html"));

            // Filter the input message to the outputWriter, using the execution context...
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);

            return result.getResultMap();
        } finally {
            smooks.close();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, SmooksException {
        System.out.println("\n\n==============Message In==============");
        System.out.println(new String(messageIn));
        System.out.println("======================================\n");

        pause("The EDI input stream can be seen above.  Press 'enter' to see this stream transformed into the Java Object model...");

        Map beans = Main.runSmooksTransform("smooks-config-sax.xml");

        System.out.println("==============Message Out=============");
        
		RollingStockList rollingstocks = (RollingStockList) beans.get( "rollingstocks" );
		for( int i = 0; i < rollingstocks.size() ; i ++ )
		{
			Rollingstock rollingstock = rollingstocks.get( i );
			System.out.println( "" );
			System.out.println( "RollingstockId : " + rollingstock.getRollingstockId() );
			System.out.println( "Schedule : " + rollingstock.getSchedule() );
			List<Vehicle> vehicles = rollingstock.getVehicles();
			for ( int y = 0 ; y < vehicles.size() ; y ++ )
			{
				Vehicle vehicle = vehicles.get( y );
				System.out.println( "Vehicle : " + y  + ": " + vehicle );
			}
			System.out.println( "Route : " + rollingstock.getRoute() );
		}
		System.out.println( "======================================\n\n");

        pause("And that's it!  Press 'enter' to finish...");
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message.edi"));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>".getBytes();
        }
    }

    private static void pause(String message) {
        System.out.println("> " + message);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            in.readLine();
        } catch (IOException e) {
        }
        System.out.println("\n");
    }
}
