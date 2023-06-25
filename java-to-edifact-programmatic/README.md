About this example
==================

Constructs a document in Java to then turn into XML with JAXB and feed the XML into Smooks in order to obtain EDIFACT. Smooks is configured programmatically which allows us to set the parameters like the message type at run-time. Note that `Smooks#addConfiguration(...)` is **not thread-safe** but `Smooks#filterSource(...)` is.

When following this approach, it is advised that the Smooks instance is cached to avoid compiling EDIFACT schema each time. 

#### UML sequence diagram
```
     ┌────┐                                                     ┌────┐          ┌──────┐
     │Main│                                                     │JAXB│          │Smooks│
     └─┬──┘                                                     └─┬──┘          └──┬───┘
       │────┐                                                                      │    
       │    │ Construct org.smooks.edifact.binding.d03b.Interchange                │    
       │<───┘                                                                      │    
       │                                                          │                │    
       │        org.smooks.edifact.binding.d03b.Interchange       │                │    
       │ ─────────────────────────────────────────────────────────>                │    
       │                                                          │                │    
       │                            XML                           │                │    
       │ <─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                │    
       │                                                          │                │    
       │                                    XML                   │                │    
       │ ──────────────────────────────────────────────────────────────────────────>    
       │                                                          │                │    
       │                                  EDIFACT                 │                │    
       │ <─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─     
     ┌─┴──┐                                                     ┌─┴──┐          ┌──┴───┐
     │Main│                                                     │JAXB│          │Smooks│
     └────┘                                                     └────┘          └──────┘
```

#### PlantUML

```plantuml
@startuml
Main -> Main : Construct org.smooks.edifact.binding.d03b.Interchange
Main -> JAXB: org.smooks.edifact.binding.d03b.Interchange
Main <-- JAXB: XML
Main -> Smooks: XML
Main <-- Smooks: EDIFACT
@enduml
```
