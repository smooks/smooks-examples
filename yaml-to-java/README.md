About
=====

This is an example that illustrates the use of the YAML reader within Smooks

The following features are demonstrated:
  - Whitespace replacement
  - Element name replacement
  - Anchor and alias resolving (by default an anchor is referred to instead of resolved)

See the smooks-config.xml for inline comments.

See:

1. The "Main" class in src/main/java/example/Main.java.
2. The input message in input-message.jsn.
3. smooks-config.xml.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the command prompts