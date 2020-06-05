/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: JMS Consumer
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
package org.smooks.examples.jms.consumer;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>
 *
 */
public class Main implements MessageListener
{

    private static final String LISTEN_QUEUE = "smooks.exampleQueue";
    private static int messageCounter;
    private Connection connection;
    private static Main consumerMain;

    public static void main(String[] args) throws Exception {
        consumerMain = new Main();
        consumerMain.setupMessageListener();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                consumerMain.closeConnection();
            }
        });

        Object sleep = new Object();
        synchronized (sleep) {
            sleep.wait();
        }
    }

    private void setupMessageListener() throws NamingException, JMSException {
        InitialContext context = null;
    	try
		{
			context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
	        connection = connectionFactory.createConnection();
	        Session session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
	        Destination destination = (Destination)context.lookup(LISTEN_QUEUE);
	        MessageConsumer consumer = session.createConsumer( destination );
	        consumer.setMessageListener( this );
	        connection.start();
	        System.out.println("JMS Listener started");
        }
        catch (NamingException e) {
            e.printStackTrace();
            throw e;
        } catch (JMSException e) {
            e.printStackTrace();
            throw e;
        } finally
    	{
    		try { context.close(); } catch (NamingException e) { e.printStackTrace(); }
    	}
    }

    public void onMessage( Message message )
	{
		messageCounter++;
        System.out.println("\n[ Received Message[" + messageCounter + "]");
        try
		{
			System.out.println("\t[JMSMessageID : " +  message.getJMSMessageID() + "]" );
	        System.out.println("\t[JMSCorrelelationID : " +  message.getJMSCorrelationID() + "]" );
	        if ( message instanceof ObjectMessage )
	        {
	            System.out.println("\t[MessageType : ObjectMessage]");
	            System.out.println( "\t[Object : " +  ((ObjectMessage)message).getObject() + "]" );
	        }
	        else if ( message instanceof TextMessage )
	        {
	            System.out.println("\t[MessageType : TextMessage]");
	            System.out.println( "\t[Text : \n" +  ((TextMessage)message).getText() + "]" );
	        }
		} catch (JMSException e)
		{
			e.printStackTrace();
		}
        System.out.println("]");

        // Slow the processing of the messages so as to force the High Water Mark...
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection()
    {
        System.out.println("Closing JMS Listener...");
        try
        {
            if ( connection != null )
                connection.close();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}
