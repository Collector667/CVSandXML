import com.opencsv.CSVReader;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Parser {
    // словарь <город, адреса>
    HashMap<String, HashMap<Address, Integer>> directory;

    public Parser() {
        this.directory = new HashMap<>();
    }
    HashMap<String, HashMap<Address, Integer>> csv(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord = csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                // сторка csv
                String[] csvStr = nextRecord[0].split(";");

                // получение города
                String city = csvStr[0].substring(0, csvStr[0].length() - 1);;

                // заполнеие адреса
                Address address = new Address(csvStr[1].substring(1, csvStr[1].length() - 1), csvStr[2], csvStr[3]);

                // добавление адреса
                addAddressToCity(city, address);
            }
            csvReader.close();
            return this.directory;
        }
        catch (Exception e) {
            return null;
        }
    }

    HashMap<String, HashMap<Address, Integer>> xml(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");

        for (int i = 0; i < addressElements.getLength(); i++) {
            // получение атрибутов значения
            NamedNodeMap attributes = addressElements.item(i).getAttributes();

            // получение города
            String city = attributes.getNamedItem("city").getNodeValue();

            // заполнеие адреса
            Address address = new Address(attributes.getNamedItem("street").getNodeValue(),
                    attributes.getNamedItem("house").getNodeValue(),
                    attributes.getNamedItem("floor").getNodeValue());

            addAddressToCity(city, address);
        }
        return this.directory;
    }

    void addAddressToCity(String city, Address address) {
        // получение словаря <город, адреса> или его создание
        HashMap<Address, Integer> addressesOfCity = this.directory.computeIfAbsent(city, k -> new HashMap<>());

        // добавление нового адреса
        if (addressesOfCity.get(address) == null) {
            addressesOfCity.put(address, 1);
        }

        // изменение кол-ва повторений
        else {
            addressesOfCity.replace(address, addressesOfCity.get(address) + 1);
        }
    }

}
