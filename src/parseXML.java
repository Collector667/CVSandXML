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
    static HashMap<String, HashMap<Address, Integer>> parse(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");

        // словарь <город, адреса>
        HashMap<String, HashMap<Address, Integer>> directory = new HashMap<>();

        for (int i = 0; i < addressElements.getLength(); i++) {
            // получение атрибутов значения
            NamedNodeMap attributes = addressElements.item(i).getAttributes();

            // получение города
            String city = attributes.getNamedItem("city").getNodeValue();

            // заполнеие адреса
            Address address = new Address(attributes.getNamedItem("street").getNodeValue(),
                    attributes.getNamedItem("house").getNodeValue(),
                    attributes.getNamedItem("floor").getNodeValue());

            // получение словаря <город, адреса> или его создание
            HashMap<Address, Integer> addressesOfCity = directory.computeIfAbsent(city, k -> new HashMap<>());

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