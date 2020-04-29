package catalog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.time.LocalDateTime;

@Path("/api/hello")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String sayHello() {
        return "Hello REST! " + LocalDateTime.now();
    }
}
