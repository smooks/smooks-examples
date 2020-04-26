package org.smooks.examples.edifact2xml;

import org.junit.jupiter.api.Test;
import org.smooks.io.StreamUtils;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainTestCase {

    @Test
    public void testRunSmooksTransform() throws IOException, SAXException {
        String expected = StreamUtils.readStreamAsString(getClass().getResourceAsStream("/expected.xml"));
        String result = Main.runSmooksTransform();

        assertFalse(DiffBuilder.compare(expected).ignoreWhitespace().withTest(result).build().hasDifferences());
    }
}
