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
public class Speed implements Serializable
{
	static final long serialVersionUID = 2943790455843454272L;
	
	private double speed;
	private transient String unit = "s";
	
	public Speed() {}
	/**
	 * Sole constructor
	 * 
	 * @param speed	the length to set. Must be a positive value
	 */
	public Speed( double speed)
	{
		if ( speed < 0 )
			throw new IllegalArgumentException ( "length must be positive");
		this.speed = speed;
	}
	
	public void setSpeed(double speed) { this.speed = speed; }
	public double getSpeed() { return speed; }

	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }
	
	public boolean equals ( Object obj )
	{
		if ( obj == this ) return true;
		if ( obj == null ) return false;
		
		if ( obj.getClass() != this.getClass() )
			return false;
		
		Speed objSpeed = ( Speed ) obj;
		return this.speed == objSpeed.getSpeed() && this.unit == objSpeed.getUnit();
	}
	
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash * (int) speed;
		hash = 31 * hash * (unit != null ? unit.hashCode():0);
		return hash;
	}
	
	public String toString()
	{
		return "[speed:" + speed + ", unit:" + unit + "]";
	}

}
