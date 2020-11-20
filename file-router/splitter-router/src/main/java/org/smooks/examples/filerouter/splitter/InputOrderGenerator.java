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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Simply generates an sample xml
 * </p>
 * Usage: <br>
 * InputOrderGenerator filename <nrOfOrderRows>
 * </br>
 * <pre>
 *Will generate a file that looks like this: 
 *&lt;order&gt;
 *	&lt;header&gt;
 *		&lt;date>Wed Nov 15 13:45:28 EST 2006&lt;/date&gt;
 *		&lt;customer number="123123"&gt;Joe&lt;/customer&gt;
 *	&lt;/header&gt;
 *	&lt;order-items&gt;
 *		&lt;order-item&gt;
 *			&lt;product&gt;111&lt;/product&gt;
 *			&lt;quantity&gt;2&lt;/quantity&gt;
 *			&lt;price&gt;8.90&lt;/price&gt;
 *			&lt;/order-item&gt;
 *			...
 *		&lt;/order-item&gt;
 *	&lt;/order-items&gt;
 *&lt;order&gt;
 * </pre>
 * The number of order-items is controlled by the 'nrOfOrderRows' argument.
 * 
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>			
 *
 */
public class InputOrderGenerator
{
	private static final String LINE_SEP = System.getProperty( "line.separator" );
	
	public static void main( String[] args  ) throws IOException
	{
		if ( args.length != 2 )
		{
			printUsage();
			return;
		}
		
		String fileName = args[0];
		String nrOfOrderItemRows = args[1];
		int nrOfOrderItems = Integer.parseInt( nrOfOrderItemRows );
		
		File file = new File ( fileName );
		deleteFile( file );
		FileWriter writer = new FileWriter( file, true );
		writer.write( "<order id='332'>" + LINE_SEP );
		writer.write( "<header>" + LINE_SEP);
		writer.write( "<customer number=\"123\">Joe</customer>" + LINE_SEP );
		writer.write( "</header>" + LINE_SEP);
		writer.flush();
		writer.write( "<order-items>" + LINE_SEP );
		System.out.print("Generating " + file.getName() + "...");
		for ( int i = 1 ; i <= nrOfOrderItems ; i ++ )
		{
    		writer.write( "<order-item id='" + i + "'>" + LINE_SEP );
    		writer.write( "<product>" + i + "</product>" + LINE_SEP );
    		writer.write( "<quantity>2</quantity>" + LINE_SEP );
    		writer.write( "<price>8.80</price>" + LINE_SEP );
    		writer.write( "</order-item>" + LINE_SEP );
    		writer.flush();
		}
		writer.write( "</order-items>" + LINE_SEP);
		writer.write( "</order>" + LINE_SEP );
		writer.close();
		System.out.println();
		System.out.println("Generated " + file.getName() + " size = [" + getFileLength( file ) + "]" );
	}
	
	private static String getFileLength( final File file )
	{
		long fileLength = file.length();
		int digits = String.valueOf( fileLength ).length();
		if ( digits < 4 )
		{
    		return fileLength + " Byte(s)";
		}
		else if ( digits >= 4 && digits <= 6 )
		{
    		return fileLength / 1024 + " KB";
		}
		else 
		{
    		return fileLength / (1024 * 1024 ) + " MB";
		}
	}
	
	private static void deleteFile( final File file ) throws IOException
	{
		if ( file.exists() )
		{
			boolean delete = file.delete();
			if ( !delete )
			{
				throw new IOException ("Could not delete file [" + file.getAbsolutePath() + "]" );
			}
		}
	}
	
	public static void printUsage()
	{
		System.err.println( "Usage: InputOrderGenerator filename <nrOfOrderRows>");
	}

}
