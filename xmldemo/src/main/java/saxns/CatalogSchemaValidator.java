package saxns;

import dom.DomService;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CatalogSchemaValidator {

    public void validateSchema(Reader source) {
        try {
            var factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            var schema = factory.newSchema(
                    new StreamSource(CatalogSchemaValidator.class.getResourceAsStream("/catalog-ns.xsd")));
            var validator = schema.newValidator();
            validator.validate(new StreamSource(source));
        }catch (Exception e) {
            throw new IllegalStateException("Can not validate", e);
        }
    }

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog-ns.xml")
        ));
        try (reader) {
            new CatalogSchemaValidator().validateSchema(reader);
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
