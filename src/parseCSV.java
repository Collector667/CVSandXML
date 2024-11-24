import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.opencsv.CSVReader;

class Csv {
    List<Address> addressList = new ArrayList<>();
}

public class parseCSV {

    static HashMap parse(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord;
            String[] a = new String[0];
            HashMap<String, Csv> addresses = new HashMap<>();

            while ((nextRecord = csvReader.readNext()) != null) {
                Address address = null;
                Csv csv = null;
                for (String cell : nextRecord) {
                    a = cell.split(";");
                    address = new Address(a[1], a[2], a[3]);
                }
                csv = new Csv();
                csv.addressList.add(address);
                if (addresses.get(a[0]) == null){
                    addresses.put(a[0], csv);}
                else {
                    addresses.get(a[0]).addressList.add(address);
                }
            }
            return addresses;
        }
        catch (Exception e) {

            return null;
        }
    }
}