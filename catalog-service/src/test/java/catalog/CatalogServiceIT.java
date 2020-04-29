package catalog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.URL;

public class CatalogServiceIT {

    Endpoint endpoint;

    @BeforeEach
    void init() {
        endpoint = Endpoint.publish("http://localhost:8080/catalog",
                new CatalogService());
    }

    @AfterEach
    void stop() {
        endpoint.stop();
    }

    @Test
    void testGetCatalog() throws Exception {
        // Kliens
        URL wsdl = new URL("http://localhost:8080/catalog?wsdl");
        var service = Service.create(wsdl,
                new QName("http://training360.com/schemas/catalog", "CatalogServiceService"));
        var port = service.getPort(new QName("http://training360.com/schemas/catalog", "CatalogServicePort"),
                CatalogServiceApi.class);

        var catalog = port.getCatalog();

        catalog.getBooks().forEach(b -> System.out.println(b.getTitle()));
    }
}
