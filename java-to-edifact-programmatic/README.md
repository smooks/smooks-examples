About this example
==================

Constructs a document in Java to then turn into XML with JAXB and feed the XML into Smooks in order to obtain EDIFACT. Smooks is configured programmatically which allows us to set the parameters like the message type at run-time. 

When following this approach, it is advised that the Smooks instance is cached so that compiled EDIFACT schemas can be reused.

#### UML sequence diagram

```
     ┌────┐                                                  ┌─────────────┐          ┌──────┐
     │Main│                                                  │JAXB Provider│          │Smooks│
     └─┬──┘                                                  └──────┬──────┘          └──┬───┘
       │────┐                                                                            │    
       │    │ 𝟏 Construct org.smooks.edifact.binding.d03b.Interchange                    │    
       │<───┘                                                                            │    
       │                                                            │                    │    
       │        𝟐 org.smooks.edifact.binding.d03b.Interchange       │                    │    
       │ ───────────────────────────────────────────────────────────>                    │    
       │                                                            │                    │    
       │                            𝟑 XML                           │                    │    
       │ <─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                    │    
       │                                                            │                    │    
       │                                      𝟒 XML                 │                    │    
       │ ────────────────────────────────────────────────────────────────────────────────>    
       │                                                            │                    │    
       │                                    𝟓 EDIFACT               │                    │    
       │ <─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─     
     ┌─┴──┐                                                  ┌──────┴──────┐          ┌──┴───┐
     │Main│                                                  │JAXB Provider│          │Smooks│
     └────┘                                                  └─────────────┘          └──────┘
```

#### PlantUML

```plantuml
@startuml
autonumber

Main -> Main : Construct org.smooks.edifact.binding.d03b.Interchange
Main -> "JAXB Provider": org.smooks.edifact.binding.d03b.Interchange
Main <-- "JAXB Provider": XML
Main -> Smooks: XML
Main <-- Smooks: EDIFACT
@enduml
```
