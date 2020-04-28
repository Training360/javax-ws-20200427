package track;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

public class TrackService {

    public void writeToXml(Track catalog, Writer dest) {
        try {
            var context = JAXBContext.newInstance(Track.class);
            var marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);

            marshaller.marshal(catalog, dest);
        } catch (JAXBException e) {
            throw new IllegalStateException("Can not write", e);
        }
    }

    public static void main(String[] args) {
        var track = new Track(List.of(new Point(1, 1, LocalDateTime.now()),
                new Point(2, 2, LocalDateTime.now().plusHours(2))));

        System.out.println(track.getPoints().get(1).getTime());

        StringWriter writer = new StringWriter();
        new TrackService().writeToXml(track, writer);
        System.out.println(writer.toString());
    }

}
