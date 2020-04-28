package xpath;

import dom.DomService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class XpathService {

    private Document loadXmlWithNamespace(Reader source) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            var builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(source));
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not parse xml", e);
        }
    }

    public Node findNodeByIsbn10(Reader source, String isbn10) {
        // xpath
        try {
            var factory = XPathFactory.newInstance();

            var xpath = factory.newXPath();
            xpath.setNamespaceContext(new MyCatalogNamespaceContext());
            String query = String.format("/c:catalog/c:book[@isbn10 = \"%s\"]", isbn10);
            System.out.println(query);
            var expr = xpath.compile(query);
            return  (Node) expr.evaluate(
                    loadXmlWithNamespace(source), XPathConstants.NODE);
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not run XPath", e);
        }

    }

    public void runXPath(Reader source) {
        try {
            var factory = XPathFactory.newInstance();
            var xpath = factory.newXPath();
//            var expr = xpath.compile("/catalog/book[1]/title/text()");
//            String value = (String) expr.evaluate(
//                    new InputSource(source), XPathConstants.STRING);
            var expr = xpath.compile("//book");
            var nodes = (NodeList) expr.evaluate(
                    new InputSource(source), XPathConstants.NODESET);


            System.out.println(nodes.getLength());
            for (var i = 0; i < nodes.getLength(); i++) {
                System.out.println(((Element)nodes.item(i)).getElementsByTagName("title").item(0).getChildNodes().item(0).getNodeValue());
            }
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not run XPath", e);
        }
    }

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog-ns.xml")
        ));
        try (reader) {
//            new XpathService().runXPath(reader);

            var node = new XpathService().findNodeByIsbn10(reader, "1590597060");
            System.out.println("N:" + node);
            System.out.println(((Element)node)
                    .getElementsByTagNameNS("http://training360.com/schemas/catalog",
                    "title").item(0).getChildNodes().item(0).getNodeValue());
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
