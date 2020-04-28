class: inverse, center, middle

# REST webszolgáltatások megvalósítása Java platformon

---

## Források

* Bill Burke: RESTful Java with JAX-RS (O'Reilly)

---
class: inverse, center, middle

# REST webszolgáltatások JAX-RS szabvánnyal

---

## RESTful webszolgáltatások

* Roy Fielding: Architectural Styles and the Design of Network-based Software Architectures, 2000
* Representational state transfer
* Egyedileg címezhető erőforrások (resource)
* Erőforrások különböző formátumokban elérhetőek
* Erőforrásokon végzett standard (CRUD) műveletek
* Állapotmentes (stateless)

---

## RESTful webszolgáltatások

* Létező technológiák: URI, HTTP, XML, JSON
* AJAX világ segítette az elterjedését
* Web Application Description Language (WADL) – nem elterjedt

---

## HTTP újrafelhasználása

* URL
* HTTP metódusok használata (GET, PUT, POST, DELETE)
* Mime-type
* URL paraméterek
* Fejlécek
* Státuszkódok

---

## JAX-RS


* Java EE része, Java SE-ben is használható
* Root resource classes: POJO, EJB
* Resource methods: URI & operation to method mapping

---

## BOM

* JAX-RS referencia implementáció: [Eclipse Jersey](https://projects.eclipse.org/proposals/eclipse-jersey)
* BOM

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.glassfish.jersey</groupId>
            <artifactId>jersey-bom</artifactId>
            <version>2.30.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

## Függőségek

```xml
<dependency>
    <groupId>jakarta.ws.rs</groupId>
    <artifactId>jakarta.ws.rs-api</artifactId>
    <version>2.1.6</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jersey.core</groupId>
    <artifactId>jersey-server</artifactId>
</dependency>
<dependency>
    <groupId>org.glassfish.grizzly</groupId>
    <artifactId>grizzly-http-server</artifactId>
    <version>2.4.4</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jersey.containers</groupId>
    <artifactId>jersey-container-grizzly2-http</artifactId>
</dependency>
<dependency>
    <groupId>org.glassfish.jersey.inject</groupId>
    <artifactId>jersey-hk2</artifactId>
</dependency>
<dependency>
  <groupId>org.glassfish.jersey.media</groupId>
  <artifactId>jersey-media-moxy</artifactId>
</dependency>
```

---

## Függőségek

```xml
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>2.3.2</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.3.2</version>
</dependency>
```

---

## Deployment

```java
@ApplicationPath("api")
public class RestConfig extends Application
{
}
```

---

## JAX-RS annotációk

* `@Path`
* `@Produces`
* `@Consumes`
* `@GET`, `@POST`, `@PUT`, `@DELETE`, `@HEAD` és `@OPTIONS`, `@HttpMethod`
* `@PathParam`, `@QueryParam`, `@MatrixParam`,
`@CookieParam`, `@HeaderParam` és `@FormParam`

---

## Resource

```java
@Path("employees")
public class EmployeeResource {

  @Inject
  private EmployeeServiceBean employeeServiceBean;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Employee> listEmployees() {
      return employeeServiceBean.findEmployees();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Employee findEmployeeById(@PathParam long id) {
      return employeeServiceBean.findEmployeeById(id);
  }

}
```

---

## Resource

```java
@Path("employees")
public class EmployeeResource {

  // ...

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveEmployee(Employee employeeData) {
      return Response
        .status(201)
        .entity(employeeServiceBean.saveEmployee(employeeData.getName()))
        .build();
  }

}
```

---

## Postman

* API fejlesztési életciklus támogatás
* RESTful webszolgáltatás hívások
* Egyszerre több környezet támogatása
* JavaScripttel szkriptelhető
* Automatikus tesztesetek
* Mock server
* Dokumentáció generálás
* Tesztesetek futtatása parancssorban

---

## Provider

* `MessageBodyReader`, `Writer`
* `ContextResolver`
* `ExceptionMapper`

---

## `@Context` annotáció

* A következőkbe injektálhatók
    * `Resource`
    * `Provider`
    * `Application` leszármazott

---

## `@Context` annotáció

* A következők injektálhatók
    * `Application` (önmagába nem)
    * `UriInfo`
    * `HttpHeaders`
    * `Request`
    * `SecurityContext`
    * Providers

---

## JAX-RS content handler

* `MessageBodyReader`, `Writer`
* `StreamingOutput`
* `InputStream`, `Reader`
* `File`
* `byte[]`
* `String`, `char[]` - `@Produces` charset
* `MultivaluedMap<String, String>`
* `Source`

---

## JAXB

* `@XmlRootElement`, `@XmlType`
* `JAXBElement`
* `application/xml`, `text/xml`, vagy `application/*+xml`
* `ContextResolver` `JAXBContext` példányosításához, paraméterezéséhez

---

## JAX-RS 2.0 újdonságok

* Client API
* Async
* HATEOAS (Hypermedia)
* Annotations
* Validation
* Filters and Handlers
* Content negotiation

