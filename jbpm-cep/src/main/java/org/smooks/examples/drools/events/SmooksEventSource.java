/*-
 * ========================LICENSE_START=================================
 * Drools :: Examples Fusion
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
package org.smooks.examples.drools.events;

import org.smooks.Smooks;
import org.smooks.api.bean.lifecycle.BeanContextLifecycleEvent;
import org.smooks.api.bean.lifecycle.BeanContextLifecycleObserver;
import org.smooks.api.bean.lifecycle.BeanLifecycle;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.examples.drools.model.StockTick;
import org.smooks.io.source.StreamSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SmooksEventSource implements EventSource {

    private final Smooks smooks;
    private final BlockingQueue<StockTick> inQueue = new SynchronousQueue<>();

    public SmooksEventSource() throws IOException, SAXException {
        smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
        smooks.addResourceConfigs("./smooks-config.xml");
        smooks.getApplicationContext().addBeanContextLifecycleObserver(new BeanContextObserver());
    }

    public void processFeed(final InputStream tickerFeed) {
        new Thread(() -> smooks.filterSource(new StreamSource<>(tickerFeed))).start();
    }

    public boolean hasNext() {
        // Returning true because otherwise it will exit immediately...
        return true;
    }

    public Event<?> getNext() {
        try {
            StockTick stockTick = inQueue.take();
            return new EventImpl<>(stockTick.getTimestamp(), stockTick);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Listen for StockTicker beans being created in Smooks BeanContexts and add them to the
     * StockTick inQueue...
     */
    private class BeanContextObserver implements BeanContextLifecycleObserver {

        public void onBeanLifecycleEvent(BeanContextLifecycleEvent event) {
            if (event.getLifecycle() == BeanLifecycle.END_FRAGMENT) {
                if (event.getBeanId().getName().equals("stockTick")) {
                    try {
                        inQueue.put((StockTick) event.getBean());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
