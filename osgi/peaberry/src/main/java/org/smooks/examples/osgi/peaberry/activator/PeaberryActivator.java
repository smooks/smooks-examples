/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: Peaberry
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
package org.smooks.examples.osgi.peaberry.activator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.smooks.examples.osgi.peaberry.ExampleUtil;
import org.smooks.examples.osgi.peaberry.Pojo;
import org.smooks.examples.osgi.peaberry.model.Order;

import static org.ops4j.peaberry.Peaberry.osgiModule;

/**
 * 
 * @author Daniel Bevenius
 *
 */
public class PeaberryActivator implements BundleActivator
{
    public void start(BundleContext context) throws Exception
    {
        System.out.println(context.getBundle().getHeaders().get("Bundle-Name") + " start");
        
        final Injector injector = Guice.createInjector(osgiModule(context), new SmooksModule());
        final Pojo pojo = injector.getInstance(Pojo.class);
        
        final String inputFile = (String) context.getBundle().getHeaders().get("Smooks-Input-File");
        final Order order = pojo.filter(inputFile);
        
        ExampleUtil.printOrder(order);
        
    }

    public void stop(BundleContext context) throws Exception
    {
        System.out.println(context.getBundle().getHeaders().get("Bundle-Name") + " stop");
    }
    
}
