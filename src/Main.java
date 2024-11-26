import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

class Processing {
    HashMap<String,Csv> directory;

    Processing(HashMap<String, Csv> directory) {
        this.directory = directory;
    }

    void outputDuplicates() {


        for (String city : this.directory.keySet()) {
            int Size = this.directory.get(city).addressList.size();
            for (int i = 0; i < Size-1; i++) {
                while (Objects.equals(this.directory.get(city).addressList.get(i).street, this.directory.get(city).addressList.get(i + 1).street)) {
                    this.directory.get(city).addressList.remove(i+1);
                    this.directory.get(city).addressList.get(i).count += 1;
                    Size = Size - 1;
                }
                if (this.directory.get(city).addressList.get(i).count != 1) {
                    System.out.println(city + " " +this.directory.get(city).addressList.get(i).street + " " + this.directory.get(city).addressList.get(i).count);
                }
            }
        }
    }
    void countOfhouse() {

        for (String city : this.directory.keySet()) {
            int[] countFloor = {0, 0, 0, 0, 0};
            int Size = this.directory.get(city).addressList.size();
            int k = 0;
            for (int i = 0; i < Size; i++) {
                k = Integer.parseInt(this.directory.get(city).addressList.get(i).floor);
                countFloor[k-1] += 1;
            }
            System.out.println(city + " 1 этаж: " + countFloor[0]
             + " 2 этажа: " + countFloor[1] + " 3 этажа " + countFloor[2] + " 4 этажа: " + countFloor[3] + " 5 этажей: " + countFloor[4]);
            countFloor[0] = 0;
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
        HashMap<String, HashMap<HashMap<String, String>, Integer>> directory = null;
        do {
            if (input != null) {
                typeOfFile = input.substring(input.lastIndexOf('.') + 1);
                if (!new File(input).exists()) {
                    System.out.println("Файл не найден");
                }
                else if (typeOfFile.equals("csv") || typeOfFile.equals("xml")) {
                    HashMap<String, Csv> addresses = null;
                    if (typeOfFile.equals("csv")) {
                        addresses = parseCSV.parse(input);

                    }
//                    else {
//                        //directory = parseXML.parse(input);
//                    }
                    new Processing(addresses).outputDuplicates();
                    new Processing(addresses).countOfhouse();
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