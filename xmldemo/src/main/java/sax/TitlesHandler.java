package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class TitlesHandler extends DefaultHandler {

    private List<String> titles = new ArrayList<>();

    private boolean inTitleTag = false;

    public List<String> getTitles() {
        return titles;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("title")) {
            inTitleTag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        inTitleTag = false;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inTitleTag) {
            titles.add(new String(ch, start, length));
        }
    }
}
