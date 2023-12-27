About
=====

This example shows how to use the <regex:reader> to process a somewhat arbitrary flat file format.  In this example we process a Log4J log file, splitting each log out into a <log> record and then splitting each log into 4 fields for <time>, <severity>, <category> and <message>.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input message in input.log.
3. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`