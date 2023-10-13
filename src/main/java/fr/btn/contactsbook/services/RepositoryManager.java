package fr.btn.contactsbook.services;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.services.dao.ContactDAO;
import fr.btn.contactsbook.services.dao.TextFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RepositoryManager {
    private ObservableList<Person> contactList;
    private FilteredList<Person> filteredContactList;
    private SortedList<Person> sortedContactList;
    private List<String> recentFilePaths;
    private ContactsBookApp contactsBookApp;
    private IOManager ioManager;
    private Person searchedContact;

    private static RepositoryManager repositoryManager;

    private RepositoryManager(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }
    public static RepositoryManager getInstance(ContactsBookApp contactsBookApp) {
        if(repositoryManager == null)
            repositoryManager = new RepositoryManager(contactsBookApp);

        return repositoryManager;
    }

    public void setUpRepository() {

    }

    public ObservableList<Person> getContactList() {
        return contactList;
    }
    public void setContactList(ObservableList<Person> contactList) {
        if(contactList != null)
            this.contactList = contactList;
        else
            this.contactList = FXCollections.observableArrayList();
    }
    /*public void setRecentFilePaths() {
        recentFilePaths = ioManager.read(ioManager.RECENT_OPEN_ABSOLUTE_PATH);
    }*/
    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }
    public void setIoManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

}
