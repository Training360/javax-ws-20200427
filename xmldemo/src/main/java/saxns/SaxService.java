package saxns;

import dom.DomService;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class SaxService {

    public int countBooks(Reader reader) {
        try {
            var factory = SAXParserFactory.newInstance();
            // FONTOS!!!
            factory.setNamespaceAware(true);
            var saxParser = factory.newSAXParser();
            var handler = new CounterHandler();
            saxParser.parse(new InputSource(reader), handler);
            return handler.getCount();
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not read xml", e);
        }
    }

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog-ns.xml")
        ));
        try (reader) {
            System.out.println(new SaxService().countBooks(reader));
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
