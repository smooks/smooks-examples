/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: XSLT-Groovy
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
package org.smooks.examples.xsltgroovy

import org.smooks.cdr.SmooksResourceConfiguration
import org.smooks.container.ExecutionContext
import org.smooks.delivery.sax.ng.AfterVisitor
import org.smooks.xml.DomUtils
import org.w3c.dom.Document
import org.w3c.dom.Element

import java.text.ParseException
import java.text.SimpleDateFormat 
/**
 * Date Formatting class.
 * <p/>
 * Simply parses a date field and replaces it with date "elements" that can be more easily processed by
 * something else (like xslt).
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
class DateFormatter implements AfterVisitor {

    private SimpleDateFormat dateDecodeFormat;
    private Properties outputFields;

    void setConfiguration(SmooksResourceConfiguration configuration) {
        String inputFormat = configuration.getParameterValue("input-format", String.class);
        String outputFormats = configuration.getParameterValue("output-format", String.class, "time=HH:mm\nday=dd\nmonth=MM\nyear=yy");

        assert inputFormat != null;
        assert inputFormat != '';
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        dateDecodeFormat = new SimpleDateFormat(inputFormat);
        outputFields = parseOutputFields(outputFormats);
    }

    @Override
    void visitAfter(Element element, ExecutionContext executionContext) {
        // Decode the date string...
        String dateString = element.getTextContent();
        Date date = null;
        try {
            date = dateDecodeFormat.parse(dateString);
        } catch (ParseException e) {
            date = new Date(0);
        }

        // Clear the child contents of the element...
        DomUtils.removeChildren(element);

        // Define a closure that we'll use for adding formatted date fields
        // from the decoded date...
        def addDateField = { fieldName, fieldFormat ->
            Document doc = element.getOwnerDocument();
            Element newElement = doc.createElement(fieldName);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(fieldFormat);

            element.appendChild(newElement);
            newElement.appendChild(doc.createTextNode(dateFormatter.format(date)));
        }

        // Apply the "addDateField" closure to the entries of the outputFields specified as
        // a Smooks resource parameter...
        for (entry in outputFields) {
            addDateField(entry.key, entry.value);
        }
    }

    private Properties parseOutputFields(String outputFormats) {
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(outputFormats.getBytes()));
        return properties;
    }
}
