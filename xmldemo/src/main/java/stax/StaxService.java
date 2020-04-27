package stax;

import dom.DomService;
import sax.SaxService;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class StaxService {

    public List<String> readIsbn10Numbers(Reader source) {
        try {
            List<String> titles = new ArrayList<>();

            var f = XMLInputFactory.newInstance();
            var r = f.createXMLStreamReader(source);

            while (r.hasNext()) {
                if (r.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    // Ezt kellett módosítani
                    if (r.getName().getLocalPart().equals("book")) {
                        titles.add(r.getAttributeValue(null, "isbn10"));
                    }
                }
                r.next();
            }
            return titles;
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not read", e);
        }
    }

    public List<String> readTitles(Reader source) {
        try {
            List<String> titles = new ArrayList<>();

            var f = XMLInputFactory.newInstance();
            var r = f.createXMLStreamReader(source);

            while (r.hasNext()) {
                if (r.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if (r.getName().getLocalPart().equals("title")) {
                        titles.add(r.getElementText());
                    }
                }
                r.next();
            }
            return titles;
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not read", e);
        }
    }

    public static void main(String[] args) {
            var reader = new BufferedReader(new InputStreamReader(
                    DomService.class.getResourceAsStream("/catalog.xml")
            ));
            try (reader) {
                System.out.println(new StaxService().readIsbn10Numbers(reader));
            }
            catch (IOException ioe) {
                throw new IllegalStateException("Can not read file", ioe);
            }
    }
}
