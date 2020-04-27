package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CounterHandler extends DefaultHandler {

    int count = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("START: " + qName);
        if (qName.equals("book")) {
            count ++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("END: " + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("CHARACTERS: " + new String(ch, start, length));
    }

    public int getCount() {
        return count;
    }
}
