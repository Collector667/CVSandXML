import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String combToExit = "exit";
        String input = null;
         do {
             if (input != null) {;
                 if (input.substring(input.lastIndexOf('.') + 1).equals("csv")) {
                     HashMap<String, Csv> addresses;
                     addresses = parseCSV.parse(input);
                     System.out.println(addresses);
                 }
                 else if ((input.substring(input.lastIndexOf('.') + 1).equals("xml"))) {

                 }
             }
             System.out.printf("Введите путь до файла или %s для завершения работы\n", combToExit);
             input = sc.nextLine();
        } while (!input.equals(combToExit));
        sc.close();
    }
}