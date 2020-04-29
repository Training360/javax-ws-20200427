package moxy;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoxyApiTest {

    MoxyApi api = new MoxyApi();

    @Test
    void testWrite() {
        Catalog catalog = new Catalog(new Book("059610149X", "Java and XML"), new Book("1590597060", "Pro XML Development with Java Technology"));
        String json = api.write(catalog);

        String expected =
                "{\"books\":[{\"isbn10\":\"059610149X\",\"title\":\"Java and XML\"},{\"isbn10\":\"1590597060\",\"title\":\"Pro XML Development with Java Technology\"}]}";
        JSONAssert.assertEquals(expected, json, true);
    }

    @Test
    void testParse() {
        // Given
        String json =  "{\"books\":[{\"isbn10\":\"059610149X\",\"title\":\"Java and XML\"},{\"isbn10\":\"1590597060\",\"title\":\"Pro XML Development with Java Technology\"}]}";
        // When
        Catalog catalog = api.parse(new StringReader(json));
        // Then
        assertEquals("Pro XML Development with Java Technology", catalog.getBooks().get(1).getTitle());
    }
}
