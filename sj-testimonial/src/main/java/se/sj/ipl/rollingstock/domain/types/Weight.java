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
package se.sj.ipl.rollingstock.domain.types;

import java.io.Serializable;

/**
 * 
 * @author Daniel Bevenius
 *
 */
public class Weight implements Serializable
{
	static final long serialVersionUID = -7918001602172848474L;
	
	private double weight;
	private String unit = "kg";
	
	public Weight() {} 
	
	public Weight(final double weight)
	{
		this(weight, "kg");
	}

	/**
	 * 
	 * @param weight	the weight to set. Must be a positive value
	 */
	public Weight(final double weight, final String unit )
	{
		if ( weight < 0 )
			throw new IllegalArgumentException ( "weight must be positive");
		this.weight = weight;
		this.unit = unit;
	}

	public double getWeight()
	{
		return weight;
	}
	public void setWeight(double weight)
	{
		this.weight = weight;
	}	

	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }
	
	public boolean equals( Object obj )
	{
		if ( this == obj )  return true;
		if ( obj == null)  return false;
		
		if (obj.getClass() != this.getClass()) 
			return false;
		
		Weight objWeight = ( Weight ) obj;
		return weight == objWeight.getWeight() && this.unit == objWeight.getUnit();
	}
	
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash * (int) weight;
		hash = 31 * hash * (unit != null ? unit.hashCode(): 0);
		return hash;
	}
	
	public String toString()
	{
		return "[weight:" + weight + ", unit:" + unit + "]";
	}
	

}
