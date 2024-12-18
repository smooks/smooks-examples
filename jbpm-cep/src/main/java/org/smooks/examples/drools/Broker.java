/**
 * Copyright 2010 JBoss Inc
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.smooks.examples.drools;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.conf.MBeansOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.io.ResourceFactory;
import org.smooks.examples.drools.events.Event;
import org.smooks.examples.drools.events.EventReceiver;
import org.smooks.examples.drools.model.Company;
import org.smooks.examples.drools.model.CompanyRegistry;
import org.smooks.examples.drools.model.StockTick;
import org.smooks.examples.drools.ui.BrokerWindow;

/**
 * The broker application
 *
 * @author etirelli
 */
public class Broker implements EventReceiver, BrokerServices {
    private static final String[] ASSET_FILES = {"/broker.drl", "/notify.drl", "/position.bpmn", "/position.drl"};

    private BrokerWindow window;
    private CompanyRegistry companies;
    private KieSession session;
    private EntryPoint tickStream;

    public Broker(BrokerWindow window,
                  CompanyRegistry companies) {
        super();
        this.window = window;
        this.companies = companies;
        this.session = createSession();
        this.tickStream = this.session.getEntryPoint("StockTick stream");
    }

    @SuppressWarnings("unchecked")
    public void receive(Event<?> event) {
        try {
            StockTick tick = ((Event<StockTick>) event).getObject();
            Company company = this.companies.getCompany(tick.getSymbol());
            this.tickStream.insert(tick);
            this.session.getAgenda().getAgendaGroup("evaluation").setFocus();
            this.session.fireAllRules();
            window.updateCompany(company.getSymbol());
            window.updateTick(tick);

        } catch (Exception e) {
            System.err.println("=============================================================");
            System.err.println("Unexpected exception caught: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private KieSession createSession() {
        KieBase kbase = loadRuleBase();
        KieSession session = kbase.newKieSession();
        //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger( session );
        session.setGlobal("services", this);
        session.setGlobal("kSession", session);

        for (Company company : this.companies.getCompanies()) {
            session.insert(company);
        }
        session.fireAllRules();
        return session;
    }

    private KieBase loadRuleBase() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        for (String assetFile : ASSET_FILES) {
            kfs.write("src/main/resources/" + assetFile, ResourceFactory.newClassPathResource(assetFile));
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.err.println(results.getMessages());
            System.exit(0);
        }
        KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration();
        kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
        kieBaseConfiguration.setOption(MBeansOption.ENABLED);
        KieContainer kieContainer = kieServices.newKieContainer("Stock Broker", kieBuilder.getKieModule().getReleaseId(), this.getClass().getClassLoader());

        return kieContainer.newKieBase(kieBaseConfiguration);
    }

    public void log(String message) {
        window.log(message);
    }
}
