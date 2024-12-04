import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


class Processing {
    HashMap<String, CityAddress> directory;
    Processing(HashMap<String, CityAddress> directory) {
        this.directory = directory;
    }
    //класс вывода дубликатов
    void outputDuplicates() {
        for (String city : this.directory.keySet()) {
            int Size = this.directory.get(city).addressList.size();
            for (int i = 0; i < Size-1; i++) {
                while (Objects.equals(this.directory.get(city).addressList.get(i).street, this.directory.get(city).addressList.get(i + 1).street)
                      && Objects.equals(this.directory.get(city).addressList.get(i).house, this.directory.get(city).addressList.get(i+1).house)       ) {
                    this.directory.get(city).addressList.remove(i+1);
                    this.directory.get(city).addressList.get(i).count += 1;
                    Size = Size - 1;
                }
                if (this.directory.get(city).addressList.get(i).count != 1) {
                    System.out.printf(city + " " +this.directory.get(city).addressList.get(i).street + " " + this.directory.get(city).addressList.get(i).count);
                }
            }
        }
    }
    //класс подсчета домов
    void countOfHouse() {

        for (String city : this.directory.keySet()) {
            int[] countFloor = {0, 0, 0, 0, 0};
            int Size = this.directory.get(city).addressList.size();
            int k;
            for (int i = 0; i < Size; i++) {
                k = Integer.parseInt(this.directory.get(city).addressList.get(i).floor);
                countFloor[k-1] += 1;
            }
            System.out.printf("\nГород %s имеет:\n %d одноэтажных зданий\n %d двухэтажный зданий\n %d трехэтажных зданий\n %d четырехэтажных зданий\n %d пятиэтажных зданий", city, countFloor[0], countFloor[1], countFloor[2], countFloor[3], countFloor[4]);
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
        do {
            //Ввод пути файла
            if (input != null) {
                typeOfFile = input.substring(input.lastIndexOf('.') + 1);
                if (!new File(input).exists()) {
                    System.out.println("Файл не найден");
                }
                else if (typeOfFile.equals("csv") || typeOfFile.equals("xml")) {
                    HashMap<String, CityAddress> addresses;
                    if (typeOfFile.equals("csv")) {
                        addresses = parse.CSV(input);
                    }
                    else {
                        addresses = parse.XML(input);
                    }
                    new Processing(addresses).outputDuplicates();
                    new Processing(addresses).countOfHouse();
                    endTime = new Date();
                    System.out.printf("\nВремя работы: %d мc\n", (endTime.getTime() - startTime.getTime()));
                }
            }
            System.out.printf("Введите путь до файла или %s для завершения работы\n", combToExit);
            input = sc.nextLine();
            startTime = new Date();
        } while (!input.equals(combToExit));
        sc.close();
    }
}