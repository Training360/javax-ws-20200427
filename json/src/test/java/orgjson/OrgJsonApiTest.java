package orgjson;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrgJsonApiTest {

    OrgJsonApi api = new OrgJsonApi();

    @Test
    void testWrite() {
        Catalog catalog = new Catalog(new Book("059610149X", "Java and XML"), new Book("1590597060", "Pro XML Development with Java Technology"));
        StringWriter writer = new StringWriter();
        api.write(catalog, writer);

        String expected =
                "{\"books\":[{\"isbn10\":\"059610149X\",\"title\":\"Java and XML\"},{\"isbn10\":\"1590597060\",\"title\":\"Pro XML Development with Java Technology\"}]}";
        String actual = writer.toString();
        JSONAssert.assertEquals(expected, actual, true);
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
