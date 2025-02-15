package org.apache.camel.edi.example;

import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;

public class EmbeddedBroker {
    static {
        try {
            ActiveMQServer server = ActiveMQServers.newActiveMQServer("broker.xml", null, null);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}