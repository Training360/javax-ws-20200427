package dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class DomService {

    public void writeCatalog(Catalog catalog, Writer dst) {
        // <catalog><book>Java and XML</book><book>Pro XML Development
        //            with Java Technology</book></catalog>

        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var doc = builder.newDocument();
            var catalogElement = doc.createElement("catalog");
            doc.appendChild(catalogElement);
            for (var book: catalog.getBooks()) {
                var bookElement = doc.createElement("book");
                var textElement = doc.createTextNode(book.getTitle());
                bookElement.appendChild(textElement);
                catalogElement.appendChild(bookElement);
            }

            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();
            var source = new DOMSource(doc);
            var result = new StreamResult(dst);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(source, result);
        }
        catch (Exception e) {
            throw new IllegalStateException("Can not parse XML", e);
        }


        // 1. létre kell hozni egy dokumentumot

        // factory, builder
        // builder. newDocument()

        // Document.createElement -> Element
        //appendChild()

        // DOM kiírása transformerrel
    }

    public void writeXml(Writer dst, Reader src) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var doc = builder.parse(new InputSource(src));
            var bookNode = doc.getElementsByTagName("book").item(0);
            var catalogNode = doc.getElementsByTagName("catalog").item(0);


            for (int i = 0; i < 3; i++) {
                var clonedNode = bookNode.cloneNode(true);
                catalogNode.appendChild(clonedNode);
            }

            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(dst);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");


            transformer.transform(source, result);

        }
        catch (Exception e) {
            throw new IllegalStateException("Can not parse XML", e);
        }
    }

    public Catalog readXml(Reader reader) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            var doc = builder.parse(new InputSource(reader));
            // printNode(doc);

            var catalog = new Catalog();
            catalog.setBooks(new ArrayList<>());

//            var catalogNode = doc.getChildNodes().item(1);
//            var catalogChildren = catalogNode.getChildNodes();
//            for (int i = 0; i < catalogChildren.getLength(); i++) {
//                if (catalogChildren.item(i).getNodeName().equals("book")) {
//                    var book = new Book();
////                    System.out.println(catalogChildren.item(i).getNodeName());
//                    book.setIsbn10(((Element)(catalogChildren.item(i))).getAttribute("isbn10"));
//                    if (catalogChildren.item(i).getChildNodes().getLength() > 0) {
//                        var titleElement = (Element) catalogChildren.item(i).getChildNodes().item(1);
////                        System.out.println(titleElement.getChildNodes().item(0).getNodeValue());
//                        book.setTitle(titleElement.getChildNodes().item(0).getNodeValue());
//                    }
//                    catalog.getBooks().add(book);
//                }
//            }

            var bookElements = doc.getElementsByTagName("book");
            for (int i = 0; i < bookElements.getLength(); i++) {
                var book = new Book();
                book.setIsbn10(((Element)(bookElements.item(i))).getAttribute("isbn10"));
                book.setTitle(((Element)bookElements.item(i)).getElementsByTagName("title").item(0).getChildNodes().item(0).getNodeValue());
                catalog.getBooks().add(book);
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
