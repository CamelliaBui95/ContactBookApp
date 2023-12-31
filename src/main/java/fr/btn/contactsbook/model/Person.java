package fr.btn.contactsbook.model;

import fr.btn.contactsbook.utils.DateUtil;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Person {
    private StringProperty firstname;
    private StringProperty lastname;
    private ObjectProperty<LocalDate> birthday;
    private StringProperty street;
    private StringProperty city;
    private SimpleIntegerProperty postalCode;

    public Person(String firstname, String lastname) {
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();

        this.firstname.set(firstname);
        this.lastname.set(lastname);

        this.street = new SimpleStringProperty("Some Street");
        this.postalCode = new SimpleIntegerProperty(123456);
        this.city = new SimpleStringProperty("Some City");
        this.birthday = new SimpleObjectProperty<LocalDate>(DateUtil.parse("01.01.1999"));
    }

    public Person() {
        this(null, null);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public Integer getPostalCode() {
        return postalCode.get();
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(Integer.parseInt(postalCode));
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname=" + firstname +
                ", lastname=" + lastname +
                ", birthday=" + birthday +
                ", street=" + street +
                ", city=" + city +
                ", postalCode=" + postalCode +
                '}';
    }
}
