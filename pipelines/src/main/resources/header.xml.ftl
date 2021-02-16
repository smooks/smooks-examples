<#--
 ========================LICENSE_START=================================
 Pipelines
 %%
 Copyright (C) 2020 - 2021 Smooks
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
<D96A:Interchange xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xmlns:D96A="http://www.ibm.com/dfdl/edi/un/edifact/D96A"
                  xmlns:srv="http://www.ibm.com/dfdl/edi/un/service/4.1">
<UNB>
    <S001>
        <E0001>UNOB</E0001>
        <E0002>1</E0002>
    </S001>
    <S002>
        <E0004>SENDER1</E0004>
        <E0007>14</E0007>
        <E0008>ZZUK</E0008>
    </S002>
    <S003>
        <E0010>RECEIVER1</E0010>
        <E0007>1</E0007>
        <E0014>ZZUK</E0014>
    </S003>
    <S004>
        <E0017>071101</E0017>
        <E0019>1701</E0019>
    </S004>
    <E0020>131</E0020>
    <E0026>ORDERS</E0026>
    <E0031>1</E0031>
    <E0035>1</E0035>
</UNB>
<D96A:Message>
    <UNH>
        <E0062>000000101</E0062>
        <S009>
            <E0065>ORDERS</E0065>
            <E0052>D</E0052>
            <E0054>96A</E0054>
            <E0051>UN</E0051>
        </S009>
    </UNH>
    <D96A:ORDERS>
        <BGM>
            <C002>
                <E1001>220</E1001>
            </C002>
            <E1004>128576</E1004>
            <E1225>9</E1225>
        </BGM>
        <DTM>
            <C507>
                <E2005>137</E2005>
                <E2380>20020830</E2380>
                <E2379>102</E2379>
            </C507>
        </DTM>
        <SegGrp-2>
            <NAD>
                <E3035>BY</E3035>
                <C082>
                    <E3039>123456</E3039>
                    <E3055>9</E3055>
                </C082>
                <C080>
                    <E3036>Therese House</E3036>
                </C080>
                <C059>
                    <E3042>29-30 Glasshouse Yard</E3042>
                </C059>
                <E3164>London</E3164>
                <E3251>EC1A 4JN</E3251>
                <E3207>UK</E3207>
            </NAD>
        </SegGrp-2>