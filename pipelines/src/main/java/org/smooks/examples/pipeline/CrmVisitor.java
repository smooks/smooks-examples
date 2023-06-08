/*-
 * ========================LICENSE_START=================================
 * Pipelines
 * %%
 * Copyright (C) 2020 - 2021 Smooks
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
package org.smooks.examples.pipeline;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smooks.api.ExecutionContext;
import org.smooks.api.resource.visitor.sax.ng.AfterVisitor;
import org.smooks.support.XmlUtil;
import org.w3c.dom.Element;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.post;

public class CrmVisitor implements AfterVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrmVisitor.class);

    private AsyncHttpClient asyncHttpClient;

    @PostConstruct
    public void postConstruct() {
        this.asyncHttpClient = asyncHttpClient();
    }

    @Override
    public void visitAfter(Element element, ExecutionContext executionContext) {
        Request request = post("https://httpbin.org/anything").setHeader("Content-Type", "application/xml").setBody(XmlUtil.serialize(element)).build();
        asyncHttpClient.executeRequest(request, new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) {
                if (response.getStatusCode() != 200) {
                    LOGGER.error("Error => " + response.getResponseBody(StandardCharsets.UTF_8));
                }
                return response;
            }
        });
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        asyncHttpClient.close();
    }
}
