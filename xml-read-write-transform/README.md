About
=====

This examples shows how to use the Smooks XMLBinding class to read and write different versions of an XML into a common Java Object model.  This can then be used to transform messages from one version to another by reading the XML into the common Java Object model using an XMLBinding instance configured for one version of the XML, and then writing those Java Objects back out using an XMLBinding instance configured for the other version of the XML.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The sample input messages in v1-message.xml and v2-message.xml.
3. The code in the src/main/java folder tree (.java).
4. The binding configurations in v1-binding-config.xml and v2-binding-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the command prompts