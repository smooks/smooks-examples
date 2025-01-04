/*-
 * ========================LICENSE_START=================================
 * EDI-to-Java-to-EDI
 * %%
 * Copyright (C) 2020 - 2024 Smooks
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
package org.smooks.examples.edi2java2edi;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.SaxWriter;
import jakarta.annotation.PostConstruct;
import org.smooks.api.ExecutionContext;
import org.smooks.api.resource.config.xpath.SelectorPath;
import org.smooks.api.resource.reader.SmooksXMLReader;
import org.smooks.engine.delivery.event.VisitSequence;
import org.smooks.engine.delivery.fragment.NodeFragment;
import org.smooks.engine.delivery.sax.ng.pointer.EventPointer;
import org.smooks.engine.resource.config.xpath.SelectorPathFactory;
import org.smooks.examples.edi2java2edi.model.Order;
import org.smooks.io.DocumentInputSource;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import javax.inject.Inject;

public class BeanXMLReader implements SmooksXMLReader {
    private final XStream xStream;
    private final SaxWriter xStreamSaxWriter;

    @Inject
    private String beanId;

    @Inject
    private String selector;

    private ExecutionContext executionContext;
    private SelectorPath selectorPath;

    public BeanXMLReader() {
        xStream = new XStream();
        xStream.processAnnotations(Order.class);
        xStreamSaxWriter = new SaxWriter();
    }

    @PostConstruct
    public void postConstruct() {
        selectorPath = SelectorPathFactory.newSelectorPath(selector);
    }

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
        return xStreamSaxWriter.getFeature(name);
    }

    @Override
    public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
        xStreamSaxWriter.setFeature(name, value);
    }

    @Override
    public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
        return xStreamSaxWriter.getProperty(name);
    }

    @Override
    public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
        xStreamSaxWriter.setProperty(name, value);
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        xStreamSaxWriter.setEntityResolver(entityResolver);
    }

    @Override
    public EntityResolver getEntityResolver() {
        return xStreamSaxWriter.getEntityResolver();
    }

    @Override
    public void setDTDHandler(DTDHandler dtdHandler) {
        xStreamSaxWriter.setDTDHandler(dtdHandler);
    }

    @Override
    public DTDHandler getDTDHandler() {
        return xStreamSaxWriter.getDTDHandler();
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        xStreamSaxWriter.setContentHandler(contentHandler);
    }

    @Override
    public ContentHandler getContentHandler() {
        return xStreamSaxWriter.getContentHandler();
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        xStreamSaxWriter.setErrorHandler(errorHandler);
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return xStreamSaxWriter.getErrorHandler();
    }

    @Override
    public void parse(InputSource inputSource) {
        Document document = ((DocumentInputSource) inputSource).getDocument();

        if (EventPointer.isPointer(document.getFirstChild())) {
            EventPointer eventPointer = new EventPointer(document.getFirstChild());
            if (eventPointer.getVisit().equals(VisitSequence.AFTER)) {
                if (new NodeFragment(eventPointer.dereference(executionContext)).isMatch(selectorPath, executionContext)) {
                    xStream.marshal(executionContext.getBeanContext().getBean(beanId), xStreamSaxWriter);
                }
            }
        }
    }

    @Override
    public void parse(String systemId) {

    }
}
