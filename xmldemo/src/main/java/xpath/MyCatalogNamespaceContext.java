package xpath;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.List;

public class MyCatalogNamespaceContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        System.out.println("PREFIX: " + prefix);
        return "http://training360.com/schemas/catalog";
    }

    @Override
    public String getPrefix(String namespaceURI) {
        System.out.println("URI " + namespaceURI);
        return "c";
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        System.out.println("Prefixes");
        return List.of("c").iterator();
    }
}
