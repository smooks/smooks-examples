About
=====

An example illustrating the use of the [Smooks Camel Cartridge](https://github.com/smooks/smooks-camel-cartridge/). [Apache Camel](https://camel.apache.org/) is configured in `src/main/resources/META-INF/spring/camel-context.xml` to poll a directory for `input-message.csv`. Once Camel reads the CSV file, it prints the file contents and sends it to Smooks so that the CSV is translated into XML. `<csv:reader .../>` in `smooks-config.xml` ingests the CSV stream and turns it into an XML stream . `<core:result .../>` reads the XML stream into a string and exports the string as a Smooks result which then allows Camel to print the XML output from the [`log`](https://camel.apache.org/components/3.21.x/log-component.html) component.

It is worth highlighting the following code in `src/main/resources/META-INF/spring/camel-context.xml`:

```xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
       http://camel.apache.org/schema/spring http://camel.apache.org/schema/spring/camel-spring.xsd">

	<bean class="org.smooks.examples.camel.dataformat.StartStopEventNotifier"/>

    ...
</beans>
```

`StartStopEventNotifier` listens for the Camel initialisation event in order to create and customise the Smooks instance such that the right class loader is used when executing the fat JAR. It also listens for completed exchanges in order to control the user prompts. 

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. `cp input-message.csv input-dir/`

### UML Sequence Diagram

![UML sequence diagram](docs/images/camel-csv-to-xml.png)