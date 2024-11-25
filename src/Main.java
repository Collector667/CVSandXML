import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

class Processing {
    HashMap<HashMap<String, String>, Integer> directory;

    public Processing(HashMap<HashMap<String, String>, Integer> directory) {
        this.directory = directory;
    }

    void outputDuplicates() {
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
        HashMap<String, HashMap<HashMap<String, String>, Integer>> directory;
        do {
            if (input != null) {
                typeOfFile = input.substring(input.lastIndexOf('.') + 1);
                if (!new File(input).exists()) {
                    System.out.println("Файл не найден");
                }
                else if (typeOfFile.equals("csv") || typeOfFile.equals("xml")) {
                    if (typeOfFile.equals("csv")) {
                        HashMap<String, Csv> addresses;
                        addresses = parseCSV.parse(input);
                        System.out.println(addresses);
                    }
                    else {
                        directory = parseXML.parse(input);
                        System.out.println(directory);
                    }
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