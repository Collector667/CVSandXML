import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
public class parseCSV {
    static HashMap<String, CityAddress> parse(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord;
            String[] read = new String[0];
            HashMap<String, CityAddress> addresses = new HashMap<>();
            nextRecord = csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                Address address = null;
                CityAddress city;
                for (String cell : nextRecord) {
                    read = cell.split(";");
                    read[0] = read[0].substring(0, read[0].length()-1);
                    address = new Address(read[1].substring(1, read[1].length()-1), read[2], read[3]);
                }
                city = new CityAddress();
                city.addressList.add(address);
                if (addresses.get(read[0]) == null){
                    addresses.put(read[0], city);}
                else {
                    addresses.get(read[0]).addressList.add(address);
                }
            }
            for (String key : addresses.keySet()) {
                addresses.get(key).addressList.sort(new ComparatorStreet());
                addresses.get(key).addressList.sort(new ComparatorHouse());
            }
            return addresses;
        }
        catch (Exception e) {
            return null;
        }
    }
}