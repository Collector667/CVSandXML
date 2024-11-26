import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

class Processing {
    HashMap<String, HashMap<HashMap<String, String>, Integer>> directory;
    int maxNumberOfFloor = 5;

    Processing(HashMap<String, HashMap<HashMap<String, String>, Integer>> directory) {
        this.directory = directory;
    }

    void outputDuplicates() {
        HashMap<HashMap<String, String>, Integer> addressesOfCity;
        HashMap<String, String> addressWithCity;
        for (String city : this.directory.keySet()) {
            addressesOfCity = this.directory.get(city);
            for (HashMap<String, String> address : addressesOfCity .keySet()) {
                if (addressesOfCity.get(address) > 1) {
                    addressWithCity = (HashMap<String, String>) address.clone();
                    addressWithCity.put("city", city);
                    System.out.printf("Запись %s повторяется %d раз(а)\n",
                            addressWithCity, addressesOfCity.get(address));
                }
            }
        }
    }

    void countingHouses() {
        HashMap<HashMap<String, String>, Integer> addressesOfCity;
        int[] masFloorNumber;
        masFloorNumber = new int[this.maxNumberOfFloor];
        for (int i = 0; i < this.maxNumberOfFloor; i++) {
            masFloorNumber[i] = 0;
        }
        for (String city : this.directory.keySet()) {
            addressesOfCity = this.directory.get(city);
            for (HashMap<String, String> address : addressesOfCity .keySet()) {
                masFloorNumber[Integer.parseInt(address.get("floor")) - 1]++;
            }
            System.out.printf("В городе %s\n", city);
            for (int i = 0; i < this.maxNumberOfFloor; i++) {
                System.out.printf("%d %d-этажных зданий\n", masFloorNumber[i], i + 1);
                masFloorNumber[i] = 0;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Scanner sc = new Scanner(System.in);
        String combToExit = "exit";
        String input = null;
        String typeOfFile;
        Date startTime = null;
        Date endTime;
        HashMap<String, HashMap<Address, Integer>> directory;
        do {
            if (input != null) {
                typeOfFile = input.substring(input.lastIndexOf('.') + 1);
                if (!new File(input).exists()) {
                    System.out.println("Файл не найден");
                }
                else if (typeOfFile.equals("csv") || typeOfFile.equals("xml")) {
                    if (typeOfFile.equals("csv")) {
                        directory = parseCSV.parse(input);
                    }
                    else {
                        directory = parseXML.parse(input);
                    }
                    Processing prcsng = new Processing(directory);
                    prcsng.outputDuplicates();
                    prcsng.countingHouses();
                    endTime = new Date();
                    System.out.printf("Время работы: %d мc\n", (endTime.getTime() - startTime.getTime()));
                }
            }
            System.out.printf("Введите путь до файла или %s для завершения работы\n", combToExit);
            input = sc.nextLine();
            startTime = new Date();
        } while (!input.equals(combToExit));
        sc.close();
    }
}