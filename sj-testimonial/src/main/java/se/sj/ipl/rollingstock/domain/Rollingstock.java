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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Rollingstock implements Serializable{

	private static final long serialVersionUID = -6845328518649423733L;

	private int id;
	private String rollingstockId;
	private Schedule schedule;
	private List<Vehicle> vehicles;
	private String route;
	private Timestamp currentTime;

	public Rollingstock(){}

	public Rollingstock(String rollingstockId, Schedule schedule, ArrayList<Vehicle> vehicles )
	{
		if ( rollingstockId == null)
			throw new IllegalArgumentException("rollingstockId must not be null");
		if ( route == null)
			throw new IllegalArgumentException("route must not be null");
		if ( schedule == null)
			throw new IllegalArgumentException("schedule must not be null");
		if ( vehicles == null)
			throw new IllegalArgumentException("vehicles must not be null");
        this.rollingstockId = rollingstockId;
		this.schedule = schedule;
		this.vehicles = vehicles;

	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public String getRollingstockId() { return rollingstockId; }
	public void setRollingstockId(String rollingstockId) { this.rollingstockId = rollingstockId; }

	public Schedule getSchedule(){return schedule;}
	public void setSchedule(Schedule schedule){this.schedule = schedule;}

	public List<Vehicle> getVehicles() { return vehicles; }
	public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles;}

	public String getRoute() { return route; }
	public void setRoute(String route) { this.route = route; }

	public Timestamp getCurrentTime() { return currentTime; }
	public void setCurrentTime(Timestamp currentTime) { this.currentTime = currentTime; }

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[rollingstock: id=").append(rollingstockId);
		sb.append(", rollingstockId=").append(rollingstockId);
		sb.append(", schedule=").append(schedule);
		sb.append(", vehicles=").append(vehicles);
		sb.append(", route=").append(route);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + id;
		result = PRIME * result + ((rollingstockId == null) ? 0 : rollingstockId.hashCode());
		result = PRIME * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = PRIME * result + ((vehicles == null) ? 0 : vehicles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Rollingstock other = (Rollingstock) obj;
		if (id != other.id)
			return false;
		if (rollingstockId == null) {
			if (other.rollingstockId != null)
				return false;
		} else if (!rollingstockId.equals(other.rollingstockId))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (vehicles == null) {
			if (other.vehicles != null)
				return false;
		} else if (!vehicles.equals(other.vehicles))
			return false;
		return true;
	}

}
