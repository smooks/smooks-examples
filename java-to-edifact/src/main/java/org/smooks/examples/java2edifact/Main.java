/*-
 * ========================LICENSE_START=================================
 * Java-to-EDIFACT
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
package org.smooks.examples.java2edifact;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.smooks.Smooks;
import org.smooks.edifact.binding.d03b.BGMBeginningOfMessage;
import org.smooks.edifact.binding.d03b.C002DocumentMessageName;
import org.smooks.edifact.binding.d03b.C106DocumentMessageIdentification;
import org.smooks.edifact.binding.d03b.C507DateTimePeriod;
import org.smooks.edifact.binding.d03b.C516MonetaryAmount;
import org.smooks.edifact.binding.d03b.DTMDateTimePeriod;
import org.smooks.edifact.binding.d03b.INVOIC;
import org.smooks.edifact.binding.d03b.Interchange;
import org.smooks.edifact.binding.d03b.MOAMonetaryAmount;
import org.smooks.edifact.binding.d03b.Message;
import org.smooks.edifact.binding.service.E0001SyntaxIdentifier;
import org.smooks.edifact.binding.service.E0051ControllingAgencyCoded;
import org.smooks.edifact.binding.service.E0065MessageType;
import org.smooks.edifact.binding.service.E0081SectionIdentification;
import org.smooks.edifact.binding.service.S001SyntaxIdentifier;
import org.smooks.edifact.binding.service.S002InterchangeSender;
import org.smooks.edifact.binding.service.S003InterchangeRecipient;
import org.smooks.edifact.binding.service.S004DateAndTimeOfPreparation;
import org.smooks.edifact.binding.service.S005RecipientReferencePasswordDetails;
import org.smooks.edifact.binding.service.S009MessageIdentifier;
import org.smooks.edifact.binding.service.UNA;
import org.smooks.edifact.binding.service.UNBInterchangeHeader;
import org.smooks.edifact.binding.service.UNHMessageHeader;
import org.smooks.edifact.binding.service.UNSSectionControl;
import org.smooks.edifact.binding.service.UNTMessageTrailer;
import org.smooks.edifact.binding.service.UNZInterchangeTrailer;
import org.smooks.engine.DefaultApplicationContextBuilder;
import org.smooks.io.sink.StringSink;
import org.smooks.io.source.ByteSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, JAXBException {
        System.out.println("\n" + run());
    }

    protected static String run() throws JAXBException, IOException, SAXException {
        // Build Java model        
        Interchange interchange = new Interchange().
                withUNA(new UNA().
                        withCompositeSeparator(":").
                        withFieldSeparator("+").
                        withDecimalSeparator(".").
                        withEscapeCharacter("?").
                        withRepeatSeparator("*").
                        withSegmentTerminator("'")).
                withUNB(new UNBInterchangeHeader().
                        withS001(new S001SyntaxIdentifier().
                                withE0001(E0001SyntaxIdentifier.UNOC).withE0002("4")).
                        withS002(new S002InterchangeSender().
                                withE0004("5790000274017").
                                withE0007("14")).
                        withS003(new S003InterchangeRecipient().
                                withE0010("5708601000836").
                                withE0007("14")).
                        withS004(new S004DateAndTimeOfPreparation().
                                withE0017(new BigDecimal(990420)).
                                withE0019(new BigDecimal(1137))).
                        withE0020("17").
                        withS005(new S005RecipientReferencePasswordDetails().withE0022("")).
                        withE0026("INVOIC").
                        withE0035(new BigDecimal(1))).
                withMessage(new Message().
                        withContent(new JAXBElement<>(new QName("UNH"), UNHMessageHeader.class, new UNHMessageHeader().
                                withE0062("30").
                                withS009(new S009MessageIdentifier().
                                        withE0065(E0065MessageType.INVOIC).
                                        withE0052("D").
                                        withE0054("03B").
                                        withE0051(E0051ControllingAgencyCoded.UN)))).
                        withContent(new JAXBElement<>(new QName("http://www.ibm.com/dfdl/edi/un/edifact/D03B", "INVOIC", "D03B"), INVOIC.class, new INVOIC().
                                withBGM(new BGMBeginningOfMessage().
                                        withC002(new C002DocumentMessageName().withE1001("380")).
                                        withC106(new C106DocumentMessageIdentification().withE1004("539602"))).
                                withDTM(new DTMDateTimePeriod().
                                        withC507(new C507DateTimePeriod().
                                                withE2005("137").
                                                withE2380("19990420").
                                                withE2379("102"))).
                                withUNS(new UNSSectionControl().
                                        withE0081(E0081SectionIdentification.S)).
                                withSegGrp50(new INVOIC.SegGrp50().
                                        withMOA(new MOAMonetaryAmount().
                                                withC516(new C516MonetaryAmount().
                                                        withE5025("64").
                                                        withE5004(new BigDecimal("100.95")).
                                                        withE6345("GBP")))))).
                        withContent(new JAXBElement<>(new QName("UNT"), UNTMessageTrailer.class, new UNTMessageTrailer().
                                withE0074(new BigDecimal(36)).
                                withE0062("30")))).
                withUNZ(new UNZInterchangeTrailer().
                        withE0036(new BigDecimal(1)).
                        withE0020("17"));

        // Turn Java model into XML       
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(Interchange.class, org.smooks.edifact.binding.service.ObjectFactory.class, org.smooks.edifact.binding.d03b.ObjectFactory.class);
        jaxbContext.createMarshaller().marshal(interchange, byteArrayOutputStream);

        // Turn XML into EDIFACT
        final Smooks smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(Main.class.getClassLoader()).build());
        smooks.addResourceConfigs("smooks-config.xml");
        StringSink stringResult = new StringSink();
        smooks.filterSource(new ByteSource(byteArrayOutputStream.toByteArray()), stringResult);

        return stringResult.getResult();
    }
}
