package jaxb;

import dom.DomService;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.List;

public class JaxbService {

    public Catalog readFromXml(Reader source) {
        try {
            var context = JAXBContext.newInstance(Catalog.class);
            var unmarshaller = context.createUnmarshaller();

            Schema schema = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(JaxbService.class.getResourceAsStream("/catalog-ns.xsd")));

            unmarshaller.setSchema(schema);

            return (Catalog) unmarshaller.unmarshal(source);

        }catch (Exception e) {
            throw new IllegalStateException("Can not read", e);
        }
    }

    public void writeToXml(Catalog catalog, Writer dest) {
        try {
            var context = JAXBContext.newInstance(Catalog.class);
            var marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);

            marshaller.marshal(catalog, dest);
        } catch (JAXBException e) {
            throw new IllegalStateException("Can not write", e);
        }
    }

    public static void main(String[] args) {
//        var catalog = new Catalog(List.of(new Book("111", "title a"),
//                new Book("222", "title b")));
//        StringWriter sw = new StringWriter();
//        new JaxbService().writeToXml(catalog, sw);
//        System.out.println(sw.toString());

        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog-ns.xml")
        ));
        try (reader) {
            var catalog = new JaxbService().readFromXml(reader);
            catalog.getBooks().forEach(b-> System.out.println(b.getTitle() + " " + b.getIsbn10()));
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
