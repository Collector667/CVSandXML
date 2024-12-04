import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
//Класс адресса
class Address  {
    public String street, house, floor;
    int count = 1;
    public Address(String street,String house, String floor) {
        this.street=street;
        this.house=house;
        this.floor=floor;
    }
    public String getStreet(){
        return street;
    }
    public String getHouse(){
        return house;
    }
}
//класс для сортировки по двум параметрам
class PersonComparators {
    public static final Comparator<Address> BY_Street_AND_House = Comparator
            .comparing(Address::getStreet)
            .thenComparing(Address::getHouse);
}
//класс списка адрресов города
class CityAddress {
    List<Address> addressList = new ArrayList<>();
}
