package employees;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeesResourceIT {

    HttpServer server;

    @BeforeEach
    void init() {
        var config = new ResourceConfig().packages("employees");
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080"),
                config);
    }

    @AfterEach
    void stop() {
        server.shutdown();
    }

    @Test
    void testCreateList() {
        ClientBuilder
                .newClient()
                .target("http://localhost:8080/api/employees")
                .request()
                .buildPost(Entity.entity(new Employee(null, "John Doe Test"),
                        MediaType.APPLICATION_JSON))
        .invoke();

        var employees =
                ClientBuilder.newClient()
                .target("http://localhost:8080/api/employees")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Employee>>(){});

//        System.out.println(employees.get(0).getName());
        assertEquals("John Doe Test", employees.get(0).getName());
    }
}
