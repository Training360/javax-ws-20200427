package catalog;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://training360.com/schemas/catalog")
public class CatalogService {

    @WebMethod
    @WebResult(name = "catalog", targetNamespace = "http://training360.com/schemas/catalog")
    public Catalog getCatalog() {
        return new Catalog(List.of(
                new Book("111", "aaa"),
                new Book("222", "bbb")
        ));
    }
}
