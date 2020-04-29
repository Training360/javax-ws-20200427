package catalog;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class CatalogService {

    @WebMethod
    public Catalog getCatalog() {
        return new Catalog(List.of(
                new Book("111", "aaa"),
                new Book("222", "bbb")
        ));
    }
}
