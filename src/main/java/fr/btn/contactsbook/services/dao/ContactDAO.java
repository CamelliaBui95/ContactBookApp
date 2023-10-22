package fr.btn.contactsbook.services.dao;

import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private TextFile textFile;
    private final String SEPARATOR = "|";
    public ContactDAO(File file) {
        this.textFile = new TextFile(file);
    }
    public ContactDAO() {

    }
    public ObservableList<Person> read() {
        List<String> contactTexts = this.textFile.read();

        ObservableList<Person> contactList = FXCollections.observableArrayList();
        int i = 0;
        while(i < contactTexts.size())
            contactList.add(parse(contactTexts.get(i++)));

        return contactList;
    }

    private Person parse(String contactText) {
        String[] texts = contactText.split("\\" + SEPARATOR);

        Person newContact = new Person();

        newContact.setFirstname(texts[0]);
        newContact.setLastname(texts[1]);
        newContact.setBirthday(DateUtil.parse(texts[2]));
        newContact.setStreet(texts[3]);
        newContact.setCity(texts[4]);
        newContact.setPostalCode(texts[5]);

        return newContact;
    }
    public void save(ObservableList<Person> contacts) {
        List<String> contactTexts = new ArrayList<>();
        int i = 0;
        while(i < contacts.size()) {
            contactTexts.add(toText(contacts.get(i)));
            i++;
        }

        textFile.writeAll(contactTexts);
    }
    private String toText(Person contact) {
        StringBuilder text = new StringBuilder();

        text.append(contact.getFirstname());
        text.append(SEPARATOR);
        text.append(contact.getLastname());
        text.append(SEPARATOR);
        text.append(DateUtil.format(contact.getBirthday()));
        text.append(SEPARATOR);
        text.append(contact.getStreet());
        text.append(SEPARATOR);
        text.append(contact.getCity());
        text.append(SEPARATOR);
        text.append(contact.getPostalCode());
        return text.toString();
    }

    public void setTextFile(File file) {
        this.textFile = new TextFile(file);
    }
}
