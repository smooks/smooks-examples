About
=====

This use case transformation has been donated by the Swedish Railway (SJ) where it is used to retrieve information about train compositions (rolling stock). The data that is the source of the transformation "lives" in a mainframe computer and is exported as a batch job to a file. The source data can be found in the file input-message.xml

In this example, we simply configure in the `<edi:parser .../>` to process the EDI stream into a Java object model.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input message in input-message.edi.
3. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the command prompts