/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: EDI-to-DB ETL
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
package org.smooks.examples.etl;

import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.persistence.jdbc.StatementExec;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.io.source.StreamSource;
import org.smooks.support.StreamUtils;
import org.smooks.testkit.HsqlServer;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple example main class.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    private HsqlServer dbServer;

    public static byte[] messageIn = readInputMessage();

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        System.out.println("\n\nThis sample will use Smooks to extract data from an EDI message an load it into a Database (Hypersonic)\n");
        Main.pause("Press return to see the sample EDI message...");

        System.out.println("\n" + new String(messageIn) + "\n");

        Main.pause("Press return to start the database...");
        main.startDatabase();
        try {
            System.out.println();
            Main.pause("The database is started now. Press return to see its contents.  It should be empty...");
            main.printOrders();
            System.out.println();
            Main.pause("Now press return to execute Smooks over the EDI message to load the database...");
            main.runSmooksTransform();
            System.out.println();
            Main.pause("Smooks has processed the message.  Now press return to view the contents of the database again.  This time there should be rows...");
            main.printOrders();
            System.out.println();
            Main.pause("And that's it! Press return exit...");
        } finally {
            main.stopDatabase();
        }
    }

    protected void runSmooksTransform() throws IOException, SAXException, SmooksException {
    	Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("./smooks-configs/smooks-config.xml");

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report.html", executionContext.getApplicationContext()));

            smooks.filterSource(executionContext, new StreamSource<>(new ByteArrayInputStream(messageIn)));
        } finally {
            smooks.close();
        }
    }

    public void printOrders() throws SQLException {
        List<Map<String, Object>> orders = getOrders();
        List<Map<String, Object>> orderItems = getOrderItems();

        printResultSet("Orders", orders);
        printResultSet("Order Items", orderItems);
    }

    public List<Map<String, Object>> getOrders() throws SQLException {
        StatementExec execOrders = new StatementExec("select * from ORDERS");
        List<Map<String, Object>> orders = execOrders.executeUnjoinedQuery(dbServer.getConnection());
        return orders;
    }

    public List<Map<String, Object>> getOrderItems() throws SQLException {
        StatementExec exec1OrderItems = new StatementExec("select * from ORDERITEMS");
        List<Map<String, Object>> orderItems = exec1OrderItems.executeUnjoinedQuery(dbServer.getConnection());
        return orderItems;
    }

    private void printResultSet(String name, List<Map<String, Object>> resultSet) {
        System.out.println(("---- " + name + " -------------------------------------------------------------------------------------------------").substring(0, 80));
        if(resultSet.isEmpty()) {
            System.out.println("(No rows)");
        } else {
            for(int i = 0; i < resultSet.size(); i++) {
                Set<Map.Entry<String, Object>> row = resultSet.get(i).entrySet();

                System.out.println("Row " + i + ":");
                for (Map.Entry<String, Object> field : row) {
                    System.out.println("\t" + field.getKey() + ":\t" + field.getValue());
                }
            }
        }
        System.out.println(("---------------------------------------------------------------------------------------------------------------------").substring(0, 80));
    }

    public void startDatabase() throws Exception {
        InputStream schema = new FileInputStream("db-create.script");

        try {
            dbServer = new HsqlServer(9201);
            dbServer.execScript(schema);
        } finally {
            schema.close();
        }
    }

    void stopDatabase() throws Exception {
        dbServer.stop();
    }

    private static byte[] readInputMessage() {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message.edi"));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>".getBytes();
        }
    }

    static void pause(String message) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("> " + message);
            in.readLine();
        } catch (IOException e) {
        }
        System.out.println("\n");
    }
}
