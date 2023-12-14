Smooks Examples
===============

![Build Status](https://github.com/smooks/smooks-examples/workflows/CI/badge.svg)

Examples are essential for helping newcomers in getting started with Smooks. This project is a catalogue of code examples illustrating the many uses of Smooks. Most of the examples are accompanied by a README file that describes the example and instructions on how to run it. [Maven 3](https://maven.apache.org/) and JDK 17 need to be installed in order to build any of the examples. All the examples are built and run in the same way:

1. Build all the examples with `mvn clean package`
2. Run an example by changing to the directory containing the example and running `mvn exec:exec`

Read the [Smooks User Guide](https://www.smooks.org/v2/documentation/) to learn more about Smooks. Example contributions in the form of [pull requests](https://github.com/smooks/smooks-examples/pulls) are more than welcome, and in fact, such contributions are encouraged since they will help the Smooks community grow.

## Catalogue

The following table briefly describes each example within this project. More information on an example can be found in the accompanying README file within the example's directory.

|           **Name**           | **Description**                                                                                                                                                                                                                                                                                                                                |
|:----------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|       camel-to-csv-xml       | Illustrates the [Camel cartridge](https://github.com/smooks/smooks-camel-cartridge) in a basic example where [Apache Camel](https://camel.apache.org/) directs the transformation from CSV into XML.                                                                                                                                           |
|       camel-dataformat       | Transforms from EDI into XML using the [unmarshal](https://camel.apache.org/components/next/eips/unmarshal-eip.html) and [marshal](https://camel.apache.org/components/next/eips/marshal-eip.html) EIPs in [Apache Camel](https://camel.apache.org/) with the help of the [Camel cartridge](https://github.com/smooks/smooks-camel-cartridge). |
|      camel-integration       | Illustrates the routing capabilities of Smooks when used from within [Apache Camel](https://camel.apache.org/).                                                                                                                                                                                                                                |
|    camel-unedifact-to-xml    | Another basic example of the [Camel cartridge](https://github.com/smooks/smooks-camel-cartridge) where [Apache Camel](https://camel.apache.org/) directs the transformation from EDIFACT into XML.                                                                                                                                             |
|    cross-domain-solution     | Uses Smooks together the [DFDL cartridge](https://github.com/smooks/smooks-dfdl-cartridge) to connect two different security domains, permitting data to flow from one domain into another while minimising the associated security risks.                                                                                                     |
|         csv-to-java          | Smooks XML configuration for binding CSV records to Java beans.                                                                                                                                                                                                                                                                                |
|   csv-to-java-programmatic   | Smooks programmatic configuration for binding CSV records to Java beans.                                                                                                                                                                                                                                                                       |
|          csv-to-xml          | Configures Smooks to turning a CSV stream into an XML stream.                                                                                                                                                                                                                                                                                  |
|         csv-to-xml-2         | Extends the `csv-to-xml` example by demonstrating how to perform XML transformation on the individual records in the CSV stream.                                                                                                                                                                                                               |
|     csv-variable-record      | Configures Smooks to ingest variable records in a CSV stream.                                                                                                                                                                                                                                                                                  |
|          dao-router          | Demonstrates routing and persistence of data access objects with the [persistence cartridge](https://github.com/smooks/smooks-persistence-cartridge).                                                                                                                                                                                          |
|  db-extract-transform-load   | Illustrates how Smooks can be used to extract data from an EDI message and load this data into a database, all without writing a single line of Java code, except for the `Main` class that executes Smooks.                                                                                                                                   |
|        drools-fusion         | Shows Smooks feeding events to [Drools](https://www.drools.org/) for [complex event processing (CEP)](https://en.wikipedia.org/wiki/Complex_event_processing) and business process management (BPM).                                                                                                                                           |
|    dynamic-model-builder     | Binds an XML document that contains multiple XML namespaces to different Java beans, depending on the namespace, and then serialises the beans back to XML.                                                                                                                                                                                    |
|         edi-to-java          | Configures Smooks to ingest an EDI stream and bind data from that stream to Java beans.                                                                                                                                                                                                                                                        |
|          edi-to-xml          | Configures Smooks to ingest an EDI stream.                                                                                                                                                                                                                                                                                                     |
|   edi-with-import-to-java    | Very similar to the `edi-to-java` example but references parent events by name when binding Java beans to EDI data.                                                                                                                                                                                                                            |
|       edifact-to-java        | Binds an EDIFACT document to a Java POJO with JAXB.                                                                                                                                                                                                                                                                                            |
|        edifact-to-xml        | Ingests an EDIFACT document to generate an XML stream that can be processed by standard XML tools (XSLT, XQuery etc) or by other Smooks components.                                                                                                                                                                                            |
|         file-router          | Smooks splitting a message into smaller messages and route the smaller messages to file.                                                                                                                                                                                                                                                       |
|     fixed-length-to-java     | Configures Smooks to turn fixed-length records into Java beans.                                                                                                                                                                                                                                                                                |
|    flatfile-to-xml-regex     | Shows how to use the <regex:reader> to process a somewhat arbitrary flat file format.                                                                                                                                                                                                                                                          |
|  freemarker-huge-transform   | Simulates the transformation of a large XML document in Smooks using [FreeMarker](https://freemarker.apache.org/index.html).                                                                                                                                                                                                                   |
|            groovy            | Demonstrates how Smooks can be used to perform fragment-based transformations using Groovy.                                                                                                                                                                                                                                                    |
|          java-basic          | Illustrates how Smooks can be used to apply a Java-based transform on a message fragment.                                                                                                                                                                                                                                                      |
|       java-to-edifact        | Smooks XML configuration for turning Java POJOs into EDIFACT.                                                                                                                                                                                                                                                                                  |
| java-to-edifact-programmatic | Smooks programmatic configuration for turning Java POJOs into EDIFACT.                                                                                                                                                                                                                                                                         |
|         java-to-java         | This example illustrates how Smooks can be used to transform one Java Object Graph to another Java Object Graph, without constructing any intermediate models.                                                                                                                                                                                 |
|         java-to-xml          | This example illustrates how Smooks can be used to transform a Java object graph to XML.  It shows how to use a JavaSource to supply a Java object graph to Smooks for transformation.                                                                                                                                                         |
|          jms-router          | Illustrates how Smooks can be used to split a message (XML in this case) and route the split messages to a JMS Queue.                                                                                                                                                                                                                          |
|         json-to-java         | Illustrates the use of the [JSON cartridge](https://github.com/smooks/smooks-json-cartridge) for reading JSON from within Smooks.                                                                                                                                                                                                              |
|      model-driven-basic      | Basic "Model Driven Transformation" using the Smooks JavaBean and Templating cartridges.  The templating is done using [FreeMarker](https://freemarker.apache.org/index.html).                                                                                                                                                                 |
|  model-driven-basic-virtual  | This example is exactly the same as the `model-driven-basic` example except that it uses maps.                                                                                                                                                                                                                                                 |
|          pipelines           | Ingests a CSV file consisting of individual sale orders with pipelines. Each individual order is transformed into JSON and XML while also an EDIFACT document aggregating the orders is created.                                                                                                                                               |
|          profiling           | An example of how Smooks can be used to profile a set of messages, and so share transformation/analysis configurations across a message set.                                                                                                                                                                                                   |
|        sj-testimonial        | Use case donated by the Swedish Railway (SJ) where EDI describing information about train compositions (rolling stock) is bound to Java beans.                                                                                                                                                                                                 |
|       validation-basic       | Illustrates how Smooks can be sed to validate XML message fragments.                                                                                                                                                                                                                                                                           |
|        xml-read-write        | Reads and write an XML message to and from a Java object model.                                                                                                                                                                                                                                                                                |
|   xml-read-write-transform   | Read and write different versions of an XML into a common Java object model.                                                                                                                                                                                                                                                                   |
|         xml-to-java          | Illustration of how Smooks can be used to extract data from an XML message, and use the data to populate an Object graph.  It also shows how this Object graph is then accessible outside Smooks via the ExecutionContext.                                                                                                                     |
|     xml-to-java-virtual      | Exactly the same as `xml-to-java´ accept that it uses maps.                                                                                                                                                                                                                                                                                    |
|          xslt-basic          | Illustrates how Smooks can be used to apply an XSLT-based transformation on a message fragment.                                                                                                                                                                                                                                                |
|         xslt-groovy          | Illustration of how Smooks can be used to combine Groovy scripting with XSLT to perform an Order message transform, simplifying the XSLT and maintaining its portability across XSLT processors.                                                                                                                                               |
|       xslt-namespaces        | An example that illustrates how Smooks can be used to apply an XSLT-based transformation on a message fragment that contains namespaces.                                                                                                                                                                                                       |
|         yaml-to-java         | Demonstrates the use of the [YAML cartridge](https://github.com/smooks/smooks-yaml-cartridge/) for reading YAML within Smooks                                                                                                                                                                                                                  |

## Smooks Execution Report

Most of the examples generate a Smooks Execution Report in the "target/report" folder of the example.  This can be a useful tool for comprehending the processing performed by Smooks.