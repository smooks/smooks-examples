About
=====

This example closely matches the [`camel-csv-to-xml`](/camel/camel-csv-to-xml/README.md) example with the notable difference that the input here is EDIFACT. 

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. `cp input-message.txt input-dir/`

### UML Sequence Diagram

![UML sequence diagram](docs/images/camel-unedifact-to-xml.png)