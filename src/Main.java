import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

class Processing {
    HashMap<String, HashMap<Address, Integer>> directory;
    // макс кол-во этажей
    int maxNumberOfFloor = 5;

    Processing(HashMap<String, HashMap<Address, Integer>> directory) {
        this.directory = directory;
    }

    // вывод дубликатов
    void outputDuplicates() {
        for (String city : this.directory.keySet()) {
            HashMap<Address, Integer> addressesOfCity = this.directory.get(city);
            for (Address address : addressesOfCity .keySet()) {
                if (addressesOfCity.get(address) > 1) {
                    System.out.printf("Запись %s повторяется %d раз(а)\n",
                            address.toString(city), addressesOfCity.get(address));
                }
            }
        }
    }

    // подсчёт домов
    void countingHouses() {
        int[] masFloorNumber;
        masFloorNumber = new int[this.maxNumberOfFloor];
        for (String city : this.directory.keySet()) {
            HashMap<Address, Integer> addressesOfCity = this.directory.get(city);
            for (Address address : addressesOfCity .keySet()) {
                masFloorNumber[Integer.parseInt(address.floor) - 1]++;
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
        Date startTime = null;
        do {
            if (input != null) {
                String typeOfFile = input.substring(input.lastIndexOf('.') + 1);
                if (!new File(input).exists()) {
                    System.out.println("Файл не найден");
                }
                else if (typeOfFile.equals("csv") || typeOfFile.equals("xml")) {
                    HashMap<String, HashMap<Address, Integer>> directory;
                    Parser parse = new Parser();
                    if (typeOfFile.equals("csv")) {

                        directory = parse.csv(input);
                    }
                    else {
                        directory = parse.xml(input);
                    }
                    Processing prcsng = new Processing(directory);
                    prcsng.outputDuplicates();
                    prcsng.countingHouses();
                    Date endTime = new Date();
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
