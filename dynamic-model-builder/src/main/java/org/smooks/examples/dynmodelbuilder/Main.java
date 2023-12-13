/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Dynamic Model Builder
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
package org.smooks.examples.dynmodelbuilder;

import org.smooks.cartridges.javabean.dynamic.BeanMetadata;
import org.smooks.cartridges.javabean.dynamic.Model;
import org.smooks.cartridges.javabean.dynamic.ModelBuilder;
import org.smooks.model.core.SmooksModel;
import org.smooks.model.javabean.Bean;
import org.xml.sax.SAXException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Main example class.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class Main {

    public static void main(String[] args) throws IOException, SAXException {

        ModelBuilder modelBuilder = new ModelBuilder("/descriptor.properties", false);
        Model<SmooksModel> model;
        SmooksModel smooksModel;

        // Read an instance of the model...
        model = modelBuilder.readModel(new FileReader("smooks-config.xml"), SmooksModel.class);
        smooksModel = model.getModelRoot();

        // Make modifications to the smooksModel instance etc....
        List<Bean> beans = smooksModel.getBeans();
        for(Bean bean : beans) {
            BeanMetadata beanMetadata = model.getBeanMetadata(bean);
            // etc ...
        }

        // Serialize the model back out...
        model.writeModel(new PrintWriter(System.out));
    }
}
