import java.util.Objects;

class Address {
    String street, house, floor;

    public Address(String street, String house, String floor) {
        this.street = street;
        this.house = house;
        this.floor = floor;
    }

    String toString(String city) {
        return String.format("{city=%s, street=%s, house=%s, floor=%s}",
                city, this.street, this.house, this.floor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Address anotherAddress = (Address) obj;

        return CharSequence.compare(this.street, anotherAddress.street) == 0
                && CharSequence.compare(this.house, anotherAddress.house) == 0;

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street, this.house);
    }
}