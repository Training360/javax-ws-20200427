package catalog;

import javax.xml.ws.Endpoint;

public class CatalogMain {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/catalog",
                new CatalogService());
    }
}
