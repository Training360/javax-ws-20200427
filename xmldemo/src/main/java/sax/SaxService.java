package sax;

import dom.DomService;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class SaxService {

    public int countBooks(Reader reader) {
        try {
            var factory = SAXParserFactory.newInstance();
            var saxParser = factory.newSAXParser();
            var handler = new CounterHandler();
            saxParser.parse(new InputSource(reader), handler);
            return handler.getCount();
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not read xml", e);
        }
    }

    public List<String> readTitles(Reader reader) {
        return List.of();
    }

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog.xml")
        ));
        try (reader) {
            System.out.println(new SaxService().readTitles(reader));
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
