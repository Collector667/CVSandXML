import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class parseXML {
    static ArrayList<HashMap<String, String>> parse(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        ArrayList<HashMap<String, String>> directory = new ArrayList<>();
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        for (int i = 0; i < addressElements.getLength(); i++) {
            NamedNodeMap attributes = addressElements.item(i).getAttributes();
            HashMap<String, String> address = new HashMap<>();
            address.put("city", attributes.getNamedItem("city").getNodeValue());
            address.put("street", attributes.getNamedItem("street").getNodeValue());
            address.put("house", attributes.getNamedItem("house").getNodeValue());
            address.put("floor", attributes.getNamedItem("floor").getNodeValue());
            directory.add(address);
        }
        return directory;
    }
}

