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
    private static void sortAddress(HashMap<String, CityAddress> addressHashMap) {
        for (String key : addressHashMap.keySet()) {
            addressHashMap.get(key).addressList =  addressHashMap.get(key).
                    addressList.stream().sorted(PersonComparators.BY_Street_AND_House).
                    collect(Collectors.toList());
        }

    }

    static HashMap<String, CityAddress> CSV(String path) {
        try {
            CSVReader csvReader = new CSVReaderHeaderAware(new FileReader(path));

            String[] nextRecord;
            String[] read;
            HashMap<String, CityAddress> addresses = new HashMap<>();

            while ((nextRecord = csvReader.readNext()) != null) {
                Address address = null;
                CityAddress city;
                String cell = nextRecord[0];
                read = cell.split(";");
                read[0] = read[0].substring(0, read[0].length()-1);
                address = new Address(read[1].substring(1, read[1].length()-1), read[2], read[3]);


                if (addresses.get(read[0]) == null){
                    city = new CityAddress();
                    city.addressList.add(address);
                    addresses.put(read[0], city);}
                else {
                    addresses.get(read[0]).addressList.add(address);
                }
            }
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
            if (addresses.get(cityname) == null){
                city = new CityAddress();
                city.addressList.add(address);
                addresses.put(cityname, city);
            }
            else {
                addresses.get(cityname).addressList.add(address);
            }
        }
        sortAddress(addresses);
        return addresses;
    }
}