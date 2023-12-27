About
=====

This example illustrates how Smooks can be used to transform a Java object graph to XML.  It shows how to use a JavaSource to supply a Java object graph to Smooks for transformation.

This example is very basic.  It performs no additional transforms over the default XML produced by Smooks when supplied a JavaSource.  You could add additional resource configurations to the smooks-config.xml to perform additional transformations on the default XML.  See the "java-to-java" example as an example of how to perform additional transforms on the XML eventPresentEvent stream produced by a JavaSource input source.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input Java model in the src/main/java/example/model package.
3. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the command prompts
