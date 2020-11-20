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

public class PassengerCarConfiguration implements Serializable
{
	static final long serialVersionUID = -309776169294059697L;
	
	private int id;
	
	private int class1;
	private int class2;
	private int couchette;
	private int compartments;
	
	public PassengerCarConfiguration()  { }
	
	public PassengerCarConfiguration(int class1, int class2, int couchette, int compartments)
	{
		this.class1 = class1;
		this.class2 = class2;
		this.couchette = couchette;
		this.compartments = compartments;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public int getClass1() { return class1; }
	public void setClass1(int class1) { this.class1 = class1; }

	public int getClass2() { return class2; }
	public void setClass2(int class2) { this.class2 = class2; }

	public int getCouchette() { return couchette; }
	public void setCouchette(int sleepSeats) { this.couchette = sleepSeats; }

	public int getCompartments() { return compartments; }
	public void setCompartments(int compartments) { this.compartments = compartments; }
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		PassengerCarConfiguration pass = ( PassengerCarConfiguration ) obj;
		return class1 == pass.class1 
			&& class2 == pass.class2 
			&& couchette == pass.couchette 
			&& compartments == pass.compartments;
	}
	
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash * class1;
		hash = 31 * hash * class2;
		hash = 31 * hash * couchette;
		hash = 31 * hash * compartments;
		return hash;
	}
	
	public String toString()
	{
		return "[class1:" + class1 + ", class2:" + class2 + ",sleepSeats:" + couchette + ",compartments:" + compartments + "]";
	}
	
}
