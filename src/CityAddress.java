import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

class PersonComparators {
    public static final Comparator<Address> BY_Street_AND_House = Comparator
            .comparing(Address::getStreet)
            .thenComparing(Address::getHouse);
}
class CityAddress {
    List<Address> addressList = new ArrayList<>();
}
