package catalog;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class CatalogMain {

    public static void main(String[] args) throws Exception {
        var config = new ResourceConfig().packages("catalog");
        var server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080"),
                config);
        System.in.read();
        server.shutdown();
    }
}
