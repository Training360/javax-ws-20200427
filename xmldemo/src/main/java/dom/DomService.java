package dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class DomService {

    public Catalog readXml(Reader reader) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var doc = builder.parse(new InputSource(reader));
            // printNode(doc);

            var catalog = new Catalog();
            catalog.setBooks(new ArrayList<>());

            var catalogNode = doc.getChildNodes().item(1);
            var catalogChildren = catalogNode.getChildNodes();
            for (int i = 0; i < catalogChildren.getLength(); i++) {
                if (catalogChildren.item(i).getNodeName().equals("book")) {
                    var book = new Book();
//                    System.out.println(catalogChildren.item(i).getNodeName());
                    book.setIsbn10(((Element)(catalogChildren.item(i))).getAttribute("isbn10"));
                    if (catalogChildren.item(i).getChildNodes().getLength() > 0) {
                        var titleElement = (Element) catalogChildren.item(i).getChildNodes().item(1);
//                        System.out.println(titleElement.getChildNodes().item(0).getNodeValue());
                        book.setTitle(titleElement.getChildNodes().item(0).getNodeValue());
                    }
                    catalog.getBooks().add(book);
                }
            }

            return catalog;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("Can not parse XML", e);
        }
    }

    private void printNode(Node node) {
        System.out.println(node.getNodeName() + " " + node.getNodeValue() + " " + node.getNodeType());
        var children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            printNode(children.item(i));
        }
    }

    public static void main(String[] args) {
        var service = new DomService();
        var reader = new BufferedReader(new InputStreamReader(
                DomService.class.getResourceAsStream("/catalog.xml")
        ));
        try (reader) {
            var catalog = service.readXml(reader);
            for (Book book: catalog.getBooks()) {
                System.out.println("Book: " + book.getIsbn10() + " " + book.getTitle());
            }
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }

    }
}
