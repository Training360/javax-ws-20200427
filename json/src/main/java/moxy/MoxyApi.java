package moxy;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.Reader;
import java.io.StringWriter;

import static org.eclipse.persistence.jaxb.UnmarshallerProperties.JSON_INCLUDE_ROOT;

public class MoxyApi {

    public Catalog parse(Reader reader) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Catalog.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setProperty(JSON_INCLUDE_ROOT, false);
            unmarshaller.setProperty("eclipselink.media-type", "application/json");

            return unmarshaller.unmarshal(new StreamSource(reader), Catalog.class).getValue();
        }
        catch (JAXBException e) {
            throw new IllegalStateException("Can not write JSON", e);
        }
    }

    public String write(Catalog catalog) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Catalog.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty("eclipselink.media-type", "application/json");
            StringWriter writer = new StringWriter();
            marshaller.marshal(catalog, writer);
            return writer.toString();
        }
        catch (JAXBException e) {
            throw new IllegalStateException("Can not write JSON", e);
        }
    }
}
