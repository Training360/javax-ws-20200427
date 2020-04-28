class: inverse, center, middle

# SOAP webszolgáltatások megvalósítása Java platformon

---

## Források

* Martin Kalin: Java Web Services Up and Running (O'Reilly)
* The Java EE 8 Tutorial (https://javaee.github.io/tutorial/)
* Mark D. Hansen: SOA Using Java Web Services (Prentice Hall)

---

## Opcionális források

* Steve Graham, Doug Davis, ...: Building Web
Services with Java: Making Sense of XML,
SOAP, WSDL, and UDDI, 2nd Edition
(Developer’s Library)
* Antonio Goncalves: Beginning Java EE 7 (Apress)
* Arun Gupta: Java EE 7 Essentials (O'Reilly)

---
class: inverse, center, middle

# SOAP webszolgáltatások JAX-WS szabvánnyal

---

## Webszolgáltatás

* W3C definíció: hálózaton keresztüli gép-gép együttműködést támogató
szoftverrendszer
* Platform független
* Szereplők
    * Szolgáltatást nyújtó
    * Szolgáltatást használni kívánó
    * Registry

---

## SOAP alapú webszolgáltatások

* Szabványokon alapuló
    * SOAP
    * WSDL
    * UDDI
* Kapcsolódó szabványok
    * HTTP
    * XML, és kapcsolódó szabványok: XSD, névterek

---

## SOAP

* W3C által karbantartott szabvány

![SOAP üzenet](images/soap-message.gif)
---

## Példa SOAP kérés

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header/>
   <soap:Body>
      <listEmployees xmlns="http://training360.com/empapp"/>
   </soap:Body>
</soap:Envelope>
```

---

## Példa SOAP válasz

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <listEmployeesResponse xmlns="http://training360.com/empapp">
         <employee>
            <id>1</id>
            <name>John Doe</name>
         </employee>
         <employee>
            <id>2</id>
            <name>Jane Doe</name>
         </employee>
      </listEmployeesResponse>
   </soap:Body>
</soap:Envelope>
```

---

## WSDL

* Web Services Description Language
* WSDL dokumentum: több állományból

---

## WSDL felépítése

![WSDL](images/wsdl.gif)

---

## Példa WSDL állomány

```xml
<?xml version='1.0' encoding='UTF-8'?>
<wsdl:definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
	xmlns:tns="http://training360.com/empapp"
	xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
	xmlns:ns1="http://schemas.xmlsoap.org/soap/http"
	name="EmployeeEndpointService"
	targetNamespace="http://training360.com/empapp">

    <!-- ... -->

</wsdl:definitions>
```

---

## Példa WSDL állomány

```xml
<wsdl:types>
    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:tns="http://training360.com/empapp"
        elementFormDefault="qualified"
        targetNamespace="http://training360.com/empapp" version="1.0">

        <xs:element name="listEmployees" type="tns:listEmployees"/>

        <xs:element name="listEmployeesResponse" type="tns:listEmployeesResponse"/>

        <xs:complexType name="listEmployees">
          <xs:sequence/>
        </xs:complexType>

        <!-- ... -->

    </xs:schema>
</wsdl:types>
```

---

## Példa WSDL állomány

```xml
<wsdl:message name="listEmployees">
  <wsdl:part element="tns:listEmployees" name="parameters">
  </wsdl:part>
</wsdl:message>
<wsdl:message name="listEmployeesResponse">
  <wsdl:part element="tns:listEmployeesResponse" name="parameters">
  </wsdl:part>
</wsdl:message>
<wsdl:portType name="EmployeeEndpoint">
  <wsdl:operation name="listEmployees">
    <wsdl:input message="tns:listEmployees" name="listEmployees">
  </wsdl:input>
    <wsdl:output message="tns:listEmployeesResponse" 
        name="listEmployeesResponse">
  </wsdl:output>
  </wsdl:operation>
</wsdl:portType>
```

---

## Példa WSDL állomány

```xml
<wsdl:binding name="EmployeeEndpointServiceSoapBinding" type="tns:EmployeeEndpoint">
  <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
  <wsdl:operation name="listEmployees">
    <soap:operation soapAction="" style="document"/>
    <wsdl:input name="listEmployees">
      <soap:body use="literal"/>
    </wsdl:input>
    <wsdl:output name="listEmployeesResponse">
      <soap:body use="literal"/>
    </wsdl:output>
  </wsdl:operation>
</wsdl:binding>
<wsdl:service name="EmployeeEndpointService">
  <wsdl:port binding="tns:EmployeeEndpointServiceSoapBinding" 
    name="EmployeeEndpointPort">
    <soap:address 
         location="http://localhost:8080/empapp/EmployeeEndpoint"/>
  </wsdl:port>
</wsdl:service>
```

---

## Implementációs megközelítések

* Megközelítések
    * Bottom up: kód alapján
    * Top down: WSDL alapján
    * Meet in the middle
* Üzleti logika != interfész
    * Két szolgáltatás, két osztály
    * Megoldás: wrapper réteg

---

## JAX-WS

* Szabvány SOAP webszolgáltatások és kliensek fejlesztésére
* Java SE-ben is használható
* Java EE-ben
    * Web alkalmazásban: POJO (nem kell EJB konténer)
    * EJB: Stateless session bean (biztonság, tranzakció-kezelés)
* Épít a JAXB-re
* Támogatja a kód és WSDL alapú megközelítést is

---

## Implementációk

* JAX-WS Reference Implementation
    * https://javaee.github.io/metro-jax-ws/
* Apache Axis2
    * http://axis.apache.org/axis2/java/core/
* CXF
    * http://cxf.apache.org/

---

## Endpoint implementáció

```java
@Stateless
@WebService(targetNamespace = "http://training360.com/empapp")
public class EmployeeEndpoint {

    @WebMethod
    @WebResult(name = "employee")
    @ResponseWrapper(localName = "employees")
    public List<Employee> listEmployees() {
        // ...
    }
}
```

---

## SOAPUi

* SOAP és RESTful webszolgáltatások tesztelésére
* Ingyenes és kereskedelmi verzió
* Webszolgáltatás hívás
* Teszt csomagok, tesztesetek, asserttek
* Szimulálás és mockolás
* Terheléses tesztelés
* Futtatás parancssorból

---

## Szerver implementálása

* `@WebService`
    * `serviceName`, `portName`
    * `targetNamespace`
    * `wsdlLocation`
* `@WebMethod` - összes publikus publikálásra kerül, ezzel csak konfigurálni
    * `operationName`
    * `action`
    * `exclude`: nem lesz benne a WSDL-ben


---

## Paraméterek és visszatérési értékek

* `@WebResult`
    * `name`
    * `targetNamespace`
* `@WebParam`
    * `name`
    * `targetNamespace`
    * `mode`: `IN` / `OUT` / `INOUT`
* `@RequestWrapper`
* `@ResponseWrapper`

---

## Kliens implementálása

* Java API
* Generált osztályok

Parancssorból

```
wsimport -d <directory> -p <pkg> -s <directory> -target <version> -keep wsdl.wsdl
```

JAX-WS Maven Plugin, `wsimport` goal

---

## Kivételkezelés

* `<Fault>` tag
* Hibakezelés
    * `RuntimeException` leszármazott (unchecked)
    * Egyéb `Exception` leszármazott (checked), `@WebFault` annotáció
    * `SOAPFaultException`

```java
SOAPFactory soapFactory = SOAPFactory.newInstance();
SOAPFault soapFault = soapFactory.createFault(
        "Cannot div parameter by 2.",
        new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client"));
throw new SOAPFaultException(soapFault);
```

---

## `WebServiceContext`

* DI (`@Resource` annotációval)
* Hozzáférés pl. a HTTP stackhez, pl. header

```java
@Resource
private WebServiceContext webServiceContext;
```

```java
Map<String, List<String>> headers = (Map<String, List<String>>) webServiceContext
  .getMessageContext()
  .get(MessageContext.HTTP_REQUEST_HEADERS);
```

---

## Request context

* A port `BindingProvider` példány

```java
BindingProvider bindingProvider = (BindingProvider) echo;
Map<String, List<String>> headers = new HashMap<>();
headers.put("foo", Arrays.asList("bar"));
bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);
```

---

## Tesztelhetőség – publikálás

* Java 6 API – Endpoint.publish
* Embedded web container
* Standalone container

---

## Tesztelhetőség - hívás

* Kliens
* SAAJ/SOAPMessage
* HTTP/XML (Parser, XPath, XMLUnit)

---

## Teljesítmény

* Message size
* Schema
* Endpoint implementation
* Handlers
* Fast infoset
* HTTP compression
* Reuse client proxy