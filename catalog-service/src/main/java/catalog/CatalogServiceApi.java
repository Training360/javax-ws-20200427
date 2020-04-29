package catalog;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://training360.com/schemas/catalog")
public interface CatalogServiceApi {

    @WebMethod
    @WebResult(name = "catalog", targetNamespace = "http://training360.com/schemas/catalog")
    Catalog getCatalog();
}
