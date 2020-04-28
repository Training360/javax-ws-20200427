package xslt;

import dom.DomService;
import org.w3c.dom.Element;
import xpath.XpathService;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XsltService {

    public void transformToHtml(Reader source, Writer dest) {
        try {
            var transformerFactory = TransformerFactory.newInstance();
            var transformer = transformerFactory.newTransformer(
                    new StreamSource(XsltService.class.getResourceAsStream("/catalog.xsl")));
            transformer.transform(new StreamSource(source),
                    new StreamResult(dest));
        } catch (Exception e) {
            throw new RuntimeException("Error transforming xml", e);
        }
    }

    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(
                XsltService.class.getResourceAsStream("/catalog.xml")
        ));
        try (reader) {
            var dest = new StringWriter();
            new XsltService().transformToHtml(reader, dest);
            System.out.println(dest.toString());
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not read file", ioe);
        }
    }
}
