import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;
//class Student implements Comparable<Student> {
//    private final String name;
//    private final double avgMark;
//
//    public Student(String name, double avgMark) {
//        this.name = name;
//        this.avgMark = avgMark;
//    }
//
//    @Override
//    public String toString() {
//        return "{n='" + name + '\'' + ", m=" + avgMark + '}';
//    }
//
//    @Override
//    public int compareTo(Student o) {
//        return name.compareTo(o.name);
//    }
//}
class Address1 implements Comparator<Address1> {
    public String street, floor;
    int count = 1;
    public Address1(String street, String floor) {
    }
    public String getStreet() {
        return street;
    }

    @Override
    public int compare(Address1 o1, Address1 o2) {
        return 0;
    }
}
class ComparatorByAvgMark implements Comparator<Address1> {
    @Override
    public int compare(Address1 o1, Address1 o2) {
        return CharSequence.compare(o1.getStreet(), o2.getStreet());
    }
}

class Csv {
    List<Address1> addressList = new ArrayList<>();
}

public class parseCSV {

    static HashMap parse(String path) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRecord;
            String[] a = new String[0];
            HashMap<String, Csv> addresses = new HashMap<>();
            nextRecord = csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                Address1 address = null;
                Csv csv = null;
                for (String cell : nextRecord) {
                    a = cell.split(";");
                    address = new Address1(a[1], a[3]);
                    address.street = a[1] + a[2];
                    address.floor = a[3];

                }
                csv = new Csv();
                csv.addressList.add(address);
                if (addresses.get(a[0]) == null){
                    addresses.put(a[0], csv);}
                else {
                    addresses.get(a[0]).addressList.add(address);
                }
            }
            for (String key : addresses.keySet()) {
                addresses.get(key).addressList.sort(new ComparatorByAvgMark());
            }

            return addresses;
        }
        catch (Exception e) {

            return null;
        }
    }
}