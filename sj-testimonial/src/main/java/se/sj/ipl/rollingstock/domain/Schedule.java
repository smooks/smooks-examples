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
/**
 * 
 */
package se.sj.ipl.rollingstock.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Schedule implements Serializable {
	
	private static final long serialVersionUID = 465582664148969314L;
	
	private int id;
	private String destination;
	private String departureStation;
	private java.sql.Date departureDate;
	private java.sql.Time departureTime;
	
	public Schedule(){}
	
	public Schedule(String destination, String departureStation, Date departureDate, Time departureTime)
	{
		if ( destination == null)
			throw new IllegalArgumentException("destination must not be null");
		if ( departureStation == null)
			throw new IllegalArgumentException("departureStation must not be null");
		if ( departureDate == null)
			throw new IllegalArgumentException("departureDate must not be null");

		this.destination = destination;
		this.departureStation = departureStation;
		this.departureDate = departureDate;
		this.departureTime = departureTime;		
	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}	
	
	public Date getDepartureDate() {return departureDate;}
	public void setDepartureDate(Date departureDate) {this.departureDate = departureDate;}
	
	public Time getDepartureTime() {return departureTime;}
	public void setDepartureTime(Time departureTime) {this.departureTime = departureTime;}
	
	public String getDestination() {return destination;}
	public void setDestination(String destination) {this.destination = destination;}
	
	public String getDepartureStation(){return departureStation;}
	public void setDepartureStation(String departureStation){this.departureStation = departureStation;}	
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((departureDate == null) ? 0 : departureDate.hashCode());
		result = PRIME * result + ((departureTime == null) ? 0 : departureTime.hashCode());
		result = PRIME * result + ((destination == null) ? 0 : destination.hashCode());
		result = PRIME * result + ((departureStation == null) ? 0 : departureStation.hashCode());
		result = PRIME * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Schedule other = (Schedule) obj;
		if (departureDate == null) {
			if (other.departureDate != null)
				return false;
		} else if (!departureDate.equals(other.departureDate))
			return false;
		if (departureTime == null) {
			if (other.departureTime != null)
				return false;
		} else if (!departureTime.equals(other.departureTime))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (departureStation == null) {
			if (other.departureStation != null)
				return false;
		} else if (!departureStation.equals(other.departureStation))
			return false;
		if (id != other.id)
			return false;
		return true;
	}	
	
	public String toString()
	{
		return "[schedule: id=" + id + ", departureStation=" + departureStation + ", departureDate" + departureDate + ", destination=" + destination + ", departureTime" + departureTime + "]";
	}
	
}
