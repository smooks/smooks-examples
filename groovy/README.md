About
=====

This is an example that illustrates how Smooks can be used to perform fragment based transforms using Groovy.

In this example, we perform a DOM based manipulation of a Shopping list message. SAX-based scripting is also supported.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input message in input-message.xml.
3. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`