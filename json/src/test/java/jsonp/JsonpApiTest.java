package jsonp;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonpApiTest {

    JsonpApi api = new JsonpApi();

    @Test
    void testWrite() {
        Catalog catalog = new Catalog(new Book("059610149X", "Java and XML"), new Book("1590597060", "Pro XML Development with Java Technology"));
        String json = api.write(catalog);

        String expected =
                "{\"books\":[{\"isbn10\":\"059610149X\",\"title\":\"Java and XML\"},{\"isbn10\":\"1590597060\",\"title\":\"Pro XML Development with Java Technology\"}]}";
        JSONAssert.assertEquals(expected, json, true);
    }

    @Test
    void testWriteAssertWithJsonPath() {
        Catalog catalog = new Catalog(new Book("059610149X", "Java and XML"), new Book("1590597060", "Pro XML Development with Java Technology"));
        String json = api.write(catalog);
        DocumentContext context = JsonPath.parse(json);
        String isbn10 = context.read("$.books[1].isbn10");
        assertEquals("1590597060", isbn10);
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
