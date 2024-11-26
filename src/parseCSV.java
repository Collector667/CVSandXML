import java.io.*;
import java.util.HashMap;
import com.opencsv.CSVReader;

public class parseCSV {
    static HashMap<String, HashMap<HashMap<String, String>, Integer>> parse(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord = csvReader.readNext();
            String[] csvStr;

            // словарь <город, адреса>
            HashMap<String, HashMap<HashMap<String, String>, Integer>> directory = new HashMap<>();

            // словарь адреса
            HashMap<String, String> address = null;

            // словарь <адрес, кол-во повторений>
            HashMap<HashMap<String, String>, Integer> addressesOfCity;

            String city = "";

            while ((nextRecord = csvReader.readNext()) != null) {;
                csvStr = nextRecord[0].split(";");

                // получение города
                city = csvStr[0];
                city = city.substring(0, city.length() - 1);

                // заполнеие словаря адреса
                address = new HashMap<>();
                address.put("street", csvStr[1].substring(1, csvStr[1].length() - 1));
                address.put("house", csvStr[2]);
                address.put("floor", csvStr[3]);

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
        catch (Exception e) {
            return null;
        }
    }
}