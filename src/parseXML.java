import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class parseXML {
    static HashMap<String, HashMap<HashMap<String, String>, Integer>> parse(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        HashMap<String, HashMap<HashMap<String, String>, Integer>> directory = new HashMap<>();
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        String city;
        HashMap<HashMap<String, String>, Integer> addressesOfCity;
        for (int i = 0; i < addressElements.getLength(); i++) {
            NamedNodeMap attributes = addressElements.item(i).getAttributes();
            HashMap<String, String> address = new HashMap<>();
            city = attributes.getNamedItem("city").getNodeValue();
            address.put("street", attributes.getNamedItem("street").getNodeValue());
            address.put("house", attributes.getNamedItem("house").getNodeValue());
            address.put("floor", attributes.getNamedItem("floor").getNodeValue());
            addressesOfCity = directory.get(city);
            if (addressesOfCity == null) {
                directory.put(city, new HashMap<>());
                directory.get(city).put(address, 1);
            }
            else {
                if (addressesOfCity.get(address) == null) {
                    addressesOfCity.put(address, 1);
                }
                else {
                    addressesOfCity.replace(address, addressesOfCity.get(address) + 1);
                }
            }
        }
        return directory;
    }
}

