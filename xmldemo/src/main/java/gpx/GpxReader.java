package gpx;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

public class GpxReader {

        public static void main(String[] args) throws Exception {
        var context = JAXBContext.newInstance(GpxType.class);
        var unmarshaller = context.createUnmarshaller();

        var gpx = (JAXBElement<GpxType>) unmarshaller.unmarshal(GpxReader.class
                .getResourceAsStream("/20200329_102821.gpx"));

        gpx.getValue().getTrk()
                .get(0).getTrkseg().get(0).getTrkpt().forEach(p -> System.out.println(p.getLat() + " " + p.getLon()));

    }
}
