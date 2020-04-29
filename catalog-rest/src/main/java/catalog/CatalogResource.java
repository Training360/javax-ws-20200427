package catalog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/catalog")
public class CatalogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Catalog getCatalog() {
        var catalog = new Catalog(List.of(new Book("111", "aaa"),
                new Book("222", "bbb")));
        return catalog;
    }
}
