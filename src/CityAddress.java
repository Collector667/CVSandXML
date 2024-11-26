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
}
class ComparatorStreet implements Comparator<Address> {
    @Override
    public int compare(Address o1, Address o2) {
        return CharSequence.compare(o1.street  , o2.street );
    }
}
class ComparatorHouse implements Comparator<Address> {
    @Override
    public int compare(Address o1, Address o2) {
        return CharSequence.compare(o1.house  , o2.house );
    }
}
class CityAddress {
    List<Address> addressList = new ArrayList<>();
}
