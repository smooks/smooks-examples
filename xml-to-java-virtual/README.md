About
=====

This example is exactly the same as "xml-to-java" accept that it uses a "Virtual Model".  This basically means that it uses Maps instead of a real object model.

A Virtual Model is probably of most use in a templating type solution. See the "model-driven-basic-virtual" example.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input message in input-message.xml.
3. The code in the src/main/java folder tree (.java).
4. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`