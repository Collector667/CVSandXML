import java.io.*;
import java.util.HashMap;
import com.opencsv.CSVReader;


public class parseCSV {
    static HashMap<String, HashMap<Address, Integer>> parse(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord = csvReader.readNext();

            // словарь <город, адреса>
            HashMap<String, HashMap<Address, Integer>> directory = new HashMap<>();

            while ((nextRecord = csvReader.readNext()) != null) {
                // сторка csv
                String[] csvStr = nextRecord[0].split(";");

                // получение города
                String city = csvStr[0].substring(0, csvStr[0].length() - 1);;

                // заполнеие адреса
                Address address = new Address(csvStr[1].substring(1, csvStr[1].length() - 1), csvStr[2], csvStr[3]);

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
            csvReader.close();
            return directory;
        }
        catch (Exception e) {
            return null;
        }
    }
}