/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: DAO Router
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
package org.smooks.examples.daorouter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.smooks.Smooks;
import org.smooks.api.ExecutionContext;
import org.smooks.api.SmooksException;
import org.smooks.cartridges.persistence.jdbc.StatementExec;
import org.smooks.cartridges.persistence.util.PersistenceUtil;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.engine.report.HtmlReportGenerator;
import org.smooks.examples.daorouter.dao.CustomerDao;
import org.smooks.examples.daorouter.dao.OrderDao;
import org.smooks.examples.daorouter.dao.ProductDao;
import org.smooks.io.source.StreamSource;
import org.smooks.scribe.adapter.mybatis.SqlSessionRegister;
import org.smooks.scribe.adapter.jpa.EntityManagerRegister;
import org.smooks.scribe.register.DaoRegister;
import org.smooks.scribe.register.MapDaoRegister;
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

    private EntityManagerFactory emf;

    private EntityManager em;

	private SqlSession sqlSession;

    public static byte[] messageInDao = readInputMessage("dao");

    public static byte[] messageInJpa = readInputMessage("jpa");

    public static byte[] messageInMyBatis = readInputMessage("mybatis");

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        System.out.println("\n\nThis sample will use Smooks to extract data from an message and load it into a Database (Hypersonic)\n");


        try {
        	Main.pause("First the database needs be started. Press return to start the database...");

        	main.startDatabase();

        	main.initDatabase();

        	main.createSqlMapInstance();

            System.out.println();

            Main.pause("The database is started now. Press return to see its contents.");

            main.printOrders();

            System.out.println();

            System.out.println("\n\nThis first run Smooks will use data access objects (DAOs) to persist and lookup entities.");

            Main.pause("Press return to see the sample message for the first run..");

            System.out.println("\n" + new String(messageInDao) + "\n");

            Main.pause("Press return to execute Smooks.");

            main.runSmooksTransformWithDao();

            System.out.println();

            Main.pause("Smooks has processed the message.  Now press return to view the contents of the database again.  This time there should be orders and orderlines...");

            main.printOrders();

            System.out.println("\n\nThis second run Smooks will use JPA to persist and lookup entities.");

            Main.pause("Press return to see the sample message for the second run..");

            System.out.println("\n" + new String(messageInJpa) + "\n");
            System.out.println();

            Main.pause("Press return to execute Smooks.");

            main.runSmooksTransformWithJpa();

            System.out.println();

            Main.pause("Smooks has processed the message.  Now press return to view the contents of the database again.  There should be new orders and orderlines...");

            main.printOrders();

            System.out.println("\n\nThis third run Smooks will use MyBatis to persist and lookup entities.");

            Main.pause("Press return to see the sample message for the second run..");

            System.out.println("\n" + new String(messageInMyBatis) + "\n");
            System.out.println();

            Main.pause("Now press return to execute Smooks.");

            main.runSmooksTransformWithMyBatis();

            Main.pause("Smooks has processed the message.  Now press return to view the contents of the database again.  There should be new orders and orderlines...");

            main.printOrders();

            Main.pause("And that's it! Press return exit...");
        } finally {
            main.stopDatabase();
        }
    }

    protected void runSmooksTransformWithDao() throws IOException, SAXException, SmooksException {

        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("./smooks-configs/smooks-dao-config.xml");

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report-dao.html", executionContext.getApplicationContext()));

            DaoRegister<Object> register =
                MapDaoRegister.builder()
                    .put("product", new ProductDao(em))
                    .put("customer", new CustomerDao(em))
                    .put("order", new OrderDao(em))
                    .build();

            PersistenceUtil.setDAORegister(executionContext, register);

            EntityTransaction tx = em.getTransaction();
            tx.begin();

            smooks.filterSource(executionContext, new StreamSource<>(new ByteArrayInputStream(messageInDao)));

            tx.commit();
        } finally {
            smooks.close();
        }
    }

    protected void runSmooksTransformWithJpa() throws IOException, SAXException, SmooksException {

        Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("./smooks-configs/smooks-jpa-config.xml");

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report-jpa.html", executionContext.getApplicationContext()));

            PersistenceUtil.setDAORegister(executionContext, new EntityManagerRegister(em));

            EntityTransaction tx = em.getTransaction();

            tx.begin();

            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageInJpa)));

            tx.commit();
        } finally {
            smooks.close();
        }
    }

    protected void runSmooksTransformWithMyBatis() throws IOException, SAXException, SmooksException {

    	Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("./smooks-configs/smooks-mybatis-config.xml");

        try {
            ExecutionContext executionContext = smooks.createExecutionContext();

            // Configure the execution context to generate a report...
            executionContext.getContentDeliveryRuntime().addExecutionEventListener(new HtmlReportGenerator("target/report/report-mybatis.html", executionContext.getApplicationContext()));

            PersistenceUtil.setDAORegister(executionContext, new SqlSessionRegister(sqlSession));
            smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageInMyBatis)));
            sqlSession.commit();
        } finally {
        	sqlSession.close();

            smooks.close();
        }
    }

    public void printOrders() throws SQLException {
    	List<Map<String, Object>> customers = getCustomers();
    	List<Map<String, Object>> products = getProducts();
        List<Map<String, Object>> orders = getOrders();
        List<Map<String, Object>> orderItems = getOrderItems();

        printResultSet("Customers", customers);
        printResultSet("Products", products);
        printResultSet("Orders", orders);
        printResultSet("Order Items", orderItems);
    }

    public List<Map<String, Object>> getOrders() throws SQLException {
    	StatementExec exec1OrderItems = new StatementExec("select * from orders");
        List<Map<String, Object>> rows = exec1OrderItems.executeUnjoinedQuery(dbServer.getConnection());
        return rows;
    }

    public List<Map<String, Object>> getOrderItems() throws SQLException {
        StatementExec exec1OrderItems = new StatementExec("select * from orderlines");
        List<Map<String, Object>> rows = exec1OrderItems.executeUnjoinedQuery(dbServer.getConnection());
        return rows;
    }

    public List<Map<String, Object>> getProducts() throws SQLException {
        StatementExec exec1OrderItems = new StatementExec("select * from products");
        List<Map<String, Object>> rows = exec1OrderItems.executeUnjoinedQuery(dbServer.getConnection());
        return rows;
    }

    public List<Map<String, Object>> getCustomers() throws SQLException {
        StatementExec exec1OrderItems = new StatementExec("select * from customers");
        List<Map<String, Object>> rows = exec1OrderItems.executeUnjoinedQuery(dbServer.getConnection());
        return rows;
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
    	dbServer = new HsqlServer(9201);
        emf = Persistence.createEntityManagerFactory("db");
        em = emf.createEntityManager();
    }

    public void initDatabase() throws Exception {
    	InputStream schema = new FileInputStream("init-db.sql");

        try {
            dbServer.execScript(schema);
        } finally {
            schema.close();
        }
    }

    void stopDatabase() throws Exception {
    	try {
			em.close();
		} catch (Exception e) {
		}
    	try {
			emf.close();
		} catch (Exception e) {
		}
        dbServer.stop();
    }

    private static byte[] readInputMessage(String msg) {
        try {
            return StreamUtils.readStream(new FileInputStream("input-message-"+ msg +".xml"));
        } catch (IOException e) {
            e.printStackTrace();
            return "<no-message/>".getBytes();
        }
    }

    private void createSqlMapInstance() {

		try {
			String resource = "/mybatis/mybatis-config.xml";
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(new InputStreamReader(this.getClass().getResourceAsStream(resource)));
			sqlSession =  sqlSessionFactory.openSession();

		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMapConfig class. Cause: " + e);
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
