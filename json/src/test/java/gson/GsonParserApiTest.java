package gson;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GsonParserApiTest {

    GsonParserApi api = new GsonParserApi();

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
