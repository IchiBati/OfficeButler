package de.ichibati.officebutler;

public class Address {
    private String street;
    private int houseNumber;
    private String houseNumberAddition;

    private int zipCode;

    private String city;

    public Address(String street, int houseNumber, String houseNumberAddition, int zipCode, String city){
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address(String street, int houseNumber, int zipCode, String city){
        this(street, houseNumber, null, zipCode, city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public void setHouseNumberAddition(String houseNumberAddition) {
        this.houseNumberAddition = houseNumberAddition;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", houseNumberAddition='" + houseNumberAddition + '\'' +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                '}';
    }
}
