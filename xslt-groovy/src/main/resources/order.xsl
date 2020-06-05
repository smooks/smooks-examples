<!--
  ========================LICENSE_START=================================
  Smooks Example :: XSLT-Groovy
  %%
  Copyright (C) 2020 Smooks
  %%
  Licensed under the terms of the Apache License Version 2.0, or
  the GNU Lesser General Public License version 3.0 or later.
  
  SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
  
  ======================================================================
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
      http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  
  ======================================================================
  
  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 3 of the License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.
  
  You should have received a copy of the GNU Lesser General Public License
  along with this program; if not, write to the Free Software Foundation,
  Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  =========================LICENSE_END==================================
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				version="1.0">

	<xsl:output method="xml" encoding="UTF-8" />

	<xsl:template match="Order">
		<Order orderId="{header/order-id}" statusCode="{header/status-code}" netAmount="{header/net-amount}"
						totalAmount="{header/total-amount}" tax="{header/tax}" date="{header/date/month}-{header/date/day}-{header/date/year}">
			<xsl:apply-templates select="customer-details"/>
			<OrderLines>
				<xsl:apply-templates select="order-item"/>
			</OrderLines>
		</Order>
	</xsl:template>

	<xsl:template match="customer-details">
		<Customer userName="{username}" firstName="{name/firstname}" lastName="{name/lastname}" state="{state}"/>
	</xsl:template>

	<xsl:template match="order-item">
        <order-item quantity="{quantity}" product-id="{product-id}" price="{price}">
			<xsl:value-of select="title" />
        </order-item>
	</xsl:template>

</xsl:stylesheet>
