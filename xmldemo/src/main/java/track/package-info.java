@XmlJavaTypeAdapter(
        value = LocalDateTimeAdatper.class, type = LocalDateTime.class
)
package track;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;