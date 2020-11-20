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
package se.sj.ipl.rollingstock.domain;

import java.io.Serializable;

public class PhoneNumber implements Serializable
{
	static final long serialVersionUID = 3273758851944676665L;
	
	private int id;
    private String function;
	private String location;
	private String number;
	
	public PhoneNumber()  { }
	
	public PhoneNumber(String function, String location, String number)
	{
		if ( function == null)
			throw new IllegalArgumentException("function must not be null");
		if ( location == null)
			throw new IllegalArgumentException("location must not be null");
		if ( number == null)
			throw new IllegalArgumentException("number must not be null");
        this.function = function;
		this.location = location;
		this.number = number;
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
    public String getFunction() { return function; }
    public void setFunction(String function) { this.function = function; }
  
	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	public String getNumber() { return number; }
	public void setNumber(String number) 
	{ 
		this.number = number; 
	}
	
	public boolean equals( Object obj )
	{
		if ( obj == this ) return true;
		if ( obj == null ) return false;
		
		if ( obj.getClass() != this.getClass() )
			return false;
		
		PhoneNumber phoneNumber = ( PhoneNumber ) obj;
		return	( function == phoneNumber.function && location == phoneNumber.location && number == phoneNumber.number)  || 
				( location != null && location.equals( phoneNumber.location ) &&  
				( number != null && number.equals( phoneNumber.number ) ) &&
				( function != null && function.equals( phoneNumber.function ) ) );
	}
	
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash * ( location == null ? 0 : location.hashCode() );
		hash = 31 * hash * ( number == null ? 0 : number.hashCode() );
		hash = 31 * hash * ( function == null ? 0 : function.hashCode() );
		return hash;
	}
	
	public String toString()
	{
		return "[function:" + function + ",location:" + location + ",number:" + number + "]";
	}
	
}
