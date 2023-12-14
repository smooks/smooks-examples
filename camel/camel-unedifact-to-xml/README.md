About
=====

This example closely matches the [`camel-csv-to-xml`](/camel/camel-csv-to-xml/README.md) example with the notable difference that the input here is EDIFACT. 

#### How to run?

1. `mvn clean install`
2. `mvn exec:exec`
3. `cp input-message.txt input-dir/`

#### UML sequence diagram

```
     ┌──────────┐                 ┌────────────┐             ┌──────┐
     │Filesystem│                 │Apache Camel│             │Smooks│
     └────┬─────┘                 └─────┬──────┘             └──┬───┘
          │ 𝟏 Poll ""input-message.txt""│                       │    
          │ <────────────────────────────                       │    
          │                             │                       │    
          │   𝟐 ""input-message.txt""   │                       │    
          │  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─>                       │    
          │                             │                       │    
          │                             │────┐                       
          │                             │    │ 𝟑 Log message body    
          │                             │<───┘                       
          │                             │                       │    
          │                             │       𝟒 EDIFACT       │    
          │                             │ ──────────────────────>    
          │                             │                       │    
          │                             │         𝟓 XML         │    
          │                             │ <─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─     
          │                             │                       │    
          │                             │────┐                       
          │                             │    │ 𝟔 Log message body    
          │                             │<───┘                       
     ┌────┴─────┐                 ┌─────┴──────┐             ┌──┴───┐
     │Filesystem│                 │Apache Camel│             │Smooks│
     └──────────┘                 └────────────┘             └──────┘
```

#### PlantUML

```plantuml
@startuml
autonumber

Filesystem <- "Apache Camel": Poll ""input-message.txt""
Filesystem --> "Apache Camel": ""input-message.txt""
"Apache Camel" -> "Apache Camel": Log message body
"Apache Camel" -> Smooks: EDIFACT
Smooks --> "Apache Camel": XML
"Apache Camel" -> "Apache Camel": Log message body
@enduml
```