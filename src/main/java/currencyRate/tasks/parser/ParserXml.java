package currencyRate.tasks.parser;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class ParserXml {

    public static XMLEventReader getReader(String url) {
        XMLEventReader reader = null;
        try {
            URL inUrl = new URL(url);
            InputStream input = inUrl.openStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            reader = factory.createXMLEventReader(url, input);
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
