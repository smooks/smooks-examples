/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Basic Model Driven
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
package org.smooks.examples.modeldrivenbasic.model;

import org.smooks.api.converter.TypeConverter;
import org.smooks.api.converter.TypeConverterDescriptor;
import org.smooks.api.converter.TypeConverterFactory;
import org.smooks.engine.converter.DefaultTypeConverterDescriptor;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Decoder for the Tracking numbers.
 * 
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class TrackingNumberTypeConverterFactory implements TypeConverterFactory<String, TrackingNumber[]> {
    
    private static Pattern lineSplitter = Pattern.compile("$", Pattern.MULTILINE);
    
    @Override
    public TypeConverter<String, TrackingNumber[]> createTypeConverter() {
        return value -> {
            // break the history up line by line - 1 tracking-number per line
            String[] unparsedTrackingNumber = lineSplitter.split(value);
            List<TrackingNumber> tnList = new Vector<TrackingNumber>(unparsedTrackingNumber.length);
            TrackingNumber[] trackingNumbers;

            // iterate over and parse the tracking-number lines
            for (int i = 0; i < unparsedTrackingNumber.length; i++) {
                String[] tokens = unparsedTrackingNumber[i].trim().split(":");

                if(tokens.length == 2) {
                    TrackingNumber trackingNumber = new TrackingNumber();

                    trackingNumber.setShipperID(tokens[0]);
                    trackingNumber.setShipmentNumber(tokens[1]);
                    tnList.add(trackingNumber);
                }
            }

            trackingNumbers = new TrackingNumber[tnList.size()];
            tnList.toArray(trackingNumbers);

            return trackingNumbers;
        };
    }

    @Override
    public TypeConverterDescriptor<Class<String>, Class<TrackingNumber[]>> getTypeConverterDescriptor() {
        return new DefaultTypeConverterDescriptor<>(String.class, TrackingNumber[].class);
    }
}
