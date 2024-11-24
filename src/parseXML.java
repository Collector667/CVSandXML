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
    static HashMap<String, ArrayList<Address>> parse(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        HashMap<String, ArrayList<Address>> directory = new HashMap<>();
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        for (int i = 0; i < addressElements.getLength(); i++) {
            NamedNodeMap attributes = addressElements.item(i).getAttributes();
            Address address = new Address(
                    attributes.getNamedItem("street").getNodeValue(),
                    attributes.getNamedItem("house").getNodeValue(),
                    attributes.getNamedItem("floor").getNodeValue()
            );

            if (directory.get(attributes.getNamedItem("city").getNodeValue()) == null) {
                ArrayList<Address> arr = new ArrayList<Address>();
                arr.add(address);
                directory.put(attributes.getNamedItem("city").getNodeValue(), arr);
            }
            else {
                directory.get(attributes.getNamedItem("city").getNodeValue()).add(address);
            }
        }
        return directory;
    }
}

