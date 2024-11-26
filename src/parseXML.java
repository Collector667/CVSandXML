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
    static HashMap<String, CityAddress> parse(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        NamedNodeMap attributes;

        // словарь <город, адреса>

        HashMap<String, CityAddress> addresses = new HashMap<>();
        // словарь адреса

        // словарь <адрес, кол-во повторений>


        String cityname;

        for (int i = 0; i < addressElements.getLength(); i++) {
            // получение атрибутов значения
            attributes = addressElements.item(i).getAttributes();
            CityAddress city = null;
            Address address = null;
            // получение города
            cityname = attributes.getNamedItem("city").getNodeValue();
            String street, house, floor;

            street = attributes.getNamedItem("street").getNodeValue();
            house = attributes.getNamedItem("house").getNodeValue();
            floor = attributes.getNamedItem("floor").getNodeValue();
            address = new Address(street, house, floor);


            city = new CityAddress();
            city.addressList.add(address);
            if (addresses.get(cityname) == null){
                addresses.put(cityname, city);
                 }
            else {
                addresses.get(cityname).addressList.add(address);
            }
        }

        for (String key : addresses.keySet()) {
            addresses.get(key).addressList.sort(new ComparatorStreet());
            addresses.get(key).addressList.sort(new ComparatorHouse());
        }

        return addresses;
    }
}