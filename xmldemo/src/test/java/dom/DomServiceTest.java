package dom;

import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomServiceTest {

    @Test
    void testReadXml() {
        var service = new DomService();
        var xml = "<catalog>\n" +
                "    <book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "        <available />\n" +
                "    </book>\n" +
                "    <book isbn10=\"1590597060\">\n" +
                "        <title>Pro XML Development with Java Technology</title>\n" +
                "    </book>\n" +
                "</catalog>";


        var catalog = service.readXml(new StringReader(xml));

        assertEquals(2, catalog.getBooks().size());

        assertEquals(List.of("Java and XML", "Pro XML Development with Java Technology"),
                catalog.getBooks().stream().map(Book::getTitle).collect(Collectors.toList()));

        assertThat(catalog.getBooks())
                .extracting(Book::getTitle)
                .containsOnly("Java and XML", "Pro XML Development with Java Technology");

//        assertThat(catalog.getBooks())
//                .extracting(Book::getIsbn10, Book::getTitle)
//                .containsOnly(tuple("059610149X", "Java and XML"),
//                        tuple("1590597060", "Pro XML Development with Java Technology"));
    }

    @Test
    void testReadXmlReadFromFile() throws IOException  {
        var service = new DomService();

        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog.xml")));
        try (reader) {
            var catalog = service.readXml(reader);

            assertEquals(2, catalog.getBooks().size());
        }
    }

    @Test
    void testWriteXml() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!-- Catalog of books --><catalog>\n" +
                "    <book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "        <available/>\n" +
                "    </book>\n" +
                "    <book isbn10=\"1590597060\">\n" +
                "        <title>Pro XML Development\n" +
                "            with Java Technology</title>\n" +
                "    </book>\n" +
                "<book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "        <available/>\n" +
                "    </book><book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "        <available/>\n" +
                "    </book><book isbn10=\"059610149X\">\n" +
                "        <title>Java and XML</title>\n" +
                "        <available/>\n" +
                "    </book></catalog>";


        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog.xml")));
        try (reader) {

            var service = new DomService();
            StringWriter writer = new StringWriter();
            service.writeXml(writer, reader);

            System.out.println(writer.toString());
//            assertEquals(expected, writer.toString());

            XmlAssert.assertThat(writer.toString())
                    .and(expected).ignoreWhitespace().ignoreComments().areSimilar();

            XmlAssert.assertThat(writer.toString())
                    .valueByXPath("/catalog/book[3]/@isbn10")
                    .isEqualTo("059610149X");
        }
    }
}
