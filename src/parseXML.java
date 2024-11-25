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
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        NamedNodeMap attributes;

        // словарь <город, адреса>
        HashMap<String, HashMap<HashMap<String, String>, Integer>> directory = new HashMap<>();

        // словарь адреса
        HashMap<String, String> address;

        // словарь <адрес, кол-во повторений>
        HashMap<HashMap<String, String>, Integer> addressesOfCity;

        String city;

        for (int i = 0; i < addressElements.getLength(); i++) {
            // получение атрибутов значения
            attributes = addressElements.item(i).getAttributes();

            // получение города
            city = attributes.getNamedItem("city").getNodeValue();

            // заполнеие словаря адреса
            address = new HashMap<>();
            address.put("street", attributes.getNamedItem("street").getNodeValue());
            address.put("house", attributes.getNamedItem("house").getNodeValue());
            address.put("floor", attributes.getNamedItem("floor").getNodeValue());

            // получение словаря <город, адреса> или его создание
            addressesOfCity = directory.computeIfAbsent(city, k -> new HashMap<>());

            // добавление нового адреса
            if (addressesOfCity.get(address) == null) {
                addressesOfCity.put(address, 1);
            }

            // изменение кол-ва повторений
            else {
                addressesOfCity.replace(address, addressesOfCity.get(address) + 1);
            }

        }
        return directory;
    }
}