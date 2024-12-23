/*-
 * ========================LICENSE_START=================================
 * Smooks Example :: EDI-to-DB ETL
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
package org.smooks.examples.etl;

import org.junit.jupiter.api.Test;
import org.smooks.Smooks;
import org.smooks.io.source.ReaderSource;
import org.xml.sax.SAXException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class DbETLTest {

	@Test
    public void test() throws Exception {

        Main main = new Main();
        main.startDatabase();

        try {
            main.runSmooksTransform();
            List<Map<String, Object>> orders = main.getOrders();
            List<Map<String, Object>> orderItems = main.getOrderItems();

            assertEquals(2, orders.size());
            assertEquals("{ORDERNUMBER=1, USERNAME=user1, STATUS=0, NET=59.97, TOTAL=64.92, ORDDATE=2006-11-15}", orders.get(0).toString());
            assertEquals("{ORDERNUMBER=2, USERNAME=user2, STATUS=0, NET=81.3, TOTAL=91.06, ORDDATE=2006-11-15}", orders.get(1).toString());
            assertEquals(4, orderItems.size());
            assertEquals("{ORDERNUMBER=1, QUANTITY=1, PRODUCT=364, TITLE=The 40-Year-Old Virgin, PRICE=28.98}", orderItems.get(0).toString());
            assertEquals("{ORDERNUMBER=1, QUANTITY=1, PRODUCT=299, TITLE=Pulp Fiction, PRICE=30.99}", orderItems.get(1).toString());
            assertEquals("{ORDERNUMBER=2, QUANTITY=2, PRODUCT=983, TITLE=Gone with The Wind, PRICE=25.8}", orderItems.get(2).toString());
            assertEquals("{ORDERNUMBER=2, QUANTITY=3, PRODUCT=299, TITLE=Lethal Weapon 2, PRICE=55.5}", orderItems.get(3).toString());
        } finally {
            main.stopDatabase();
        }
    }



    public static void main(String[] args) throws IOException, SAXException {
        //printReport("edi-orders-parser.xml");
        //writeBigFile();
        //eatBigFile();
    }

    private static void eatBigFile() throws IOException, SAXException {

        try (Smooks smooks = new Smooks("./smooks-configs/smooks-config.xml")) {
            try (FileReader reader = new FileReader("/zap/big-edi.edi")) {
                long start = System.currentTimeMillis();
                smooks.filterSource(smooks.createExecutionContext(), new ReaderSource<>(reader));
                System.out.println("Took: " + (System.currentTimeMillis() - start));
            }
        }
    }

    private static void writeBigFile() throws IOException {
        FileWriter writer = new FileWriter("/zap/big-edi.edi");

        try {
            writer.write("MLS*Wed Nov 15 13:45:28 EST 2006\n");

            String toadd = "HDR*1*0*59.97*64.92*4.95\n" +
                    "CUS*user1*Harry^Fletcher*SD\n" +
                    "ORD*1*1*364*The 40-Year-Old Virgin*28.98\n" +
                    "ORD*2*1*299*Pulp Fiction*30.99\n" +
                    "HDR*2*0*81.30*91.06*9.76\n" +
                    "CUS*user2*George^Hook*SD\n" +
                    "ORD*3*2*983*Gone with The Wind*25.80\n" +
                    "ORD*4*3*299*Lethal Weapon 2*55.50\n";

            for(int i = 0; i < 2015748; i++) {
                writer.write(toadd);
                writer.flush();
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
