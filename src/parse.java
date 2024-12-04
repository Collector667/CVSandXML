import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class parse {
    //функция сортировки
    private static void sortAddress(HashMap<String, CityAddress> addressHashMap) {
        for (String key : addressHashMap.keySet()) {
            addressHashMap.get(key).addressList =  addressHashMap.get(key).
                    addressList.stream().sorted(PersonComparators.BY_Street_AND_House).
                    collect(Collectors.toList());
        }

    }
    //функция чтения файла CSV
    static HashMap<String, CityAddress> CSV(String path) {
        try {
            //Чтение
            CSVReader csvReader = new CSVReaderHeaderAware(new FileReader(path));
            String[] nextRecord;
            String[] read;
            //Создание хешмапа с ключом Город и информация класс списка адрессов
            HashMap<String, CityAddress> addresses = new HashMap<>();
            //чтение
            while ((nextRecord = csvReader.readNext()) != null) {
                Address address;
                CityAddress city;
                String cell = nextRecord[0];
                read = cell.split(";");
                read[0] = read[0].substring(0, read[0].length()-1);
                address = new Address(read[1].substring(1, read[1].length()-1), read[2], read[3]);

                //добавление в словарь
                if (addresses.get(read[0]) == null){
                    city = new CityAddress();
                    city.addressList.add(address);
                    addresses.put(read[0], city);}
                else {
                    addresses.get(read[0]).addressList.add(address);
                }
            }
            //сортировка
            sortAddress(addresses);
            return addresses;
        }
        catch (Exception e) {
            return null;
        }
    }


    static HashMap<String, CityAddress> XML(String path)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        NodeList addressElements = document.getDocumentElement().getElementsByTagName("item");
        NamedNodeMap attributes;
        // словарь <город, адреса>
        HashMap<String, CityAddress> addresses = new HashMap<>();


        String cityName;
        for (int i = 0; i < addressElements.getLength(); i++) {
            // получение атрибутов значения
            attributes = addressElements.item(i).getAttributes();
            CityAddress city;
            Address address;
            // получение значений
            cityName = attributes.getNamedItem("city").getNodeValue();
            String street, house, floor;
            street = attributes.getNamedItem("street").getNodeValue();
            house = attributes.getNamedItem("house").getNodeValue();
            floor = attributes.getNamedItem("floor").getNodeValue();
            //добавление в словарь
            address = new Address(street, house, floor);
            if (addresses.get(cityName) == null){
                city = new CityAddress();
                city.addressList.add(address);
                addresses.put(cityName, city);
            }
            else {
                addresses.get(cityName).addressList.add(address);
            }
        }
        //сортировка
        sortAddress(addresses);
        return addresses;
    }
}