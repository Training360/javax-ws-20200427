package jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;

public class JacksonApi {

    ObjectMapper objectMapper = new ObjectMapper();

    public JacksonApi() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public Catalog parse(Reader reader) {
        try {
            return objectMapper.readValue(reader, Catalog.class);
        }
        catch (IOException e) {
            throw new IllegalStateException("Can not parse JSON", e);
        }
    }

    public String write(Catalog catalog) {
        try {
            return objectMapper.writeValueAsString(catalog);
        }
        catch (JsonProcessingException e) {
            throw new IllegalStateException("Can not write JSON", e);
        }
    }
}
