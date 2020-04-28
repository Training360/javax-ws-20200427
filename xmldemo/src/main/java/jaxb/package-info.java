@XmlSchema(
        elementFormDefault= XmlNsForm.QUALIFIED,
        namespace="http://training360.com/schemas/catalog",
        xmlns={@XmlNs(prefix="c",
                namespaceURI="http://training360.com/schemas/catalog")}
)
package jaxb;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;