package fr.btn.contactsbook.services;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.services.dao.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class RepositoryManager {
    private ObservableList<Person> contactList;
    private FilteredList<Person> filteredContactList;
    private SortedList<Person> sortedContactList;
    private List<String> recentFilePaths;
    private IOManager ioManager;
    private String searchedContact;
    private Stage mainWindow;

    private File contactFile;

    private static RepositoryManager repositoryManager;

    private RepositoryManager() {

    }
    public static RepositoryManager getInstance() {
        if(repositoryManager == null)
            repositoryManager = new RepositoryManager();

        return repositoryManager;
    }

    public void setUpRepository() {
        contactList = FXCollections.observableArrayList();
        filteredContactList = new FilteredList<>(contactList, null);
        sortedContactList = new SortedList<>(filteredContactList);

        setRecentFilePaths();
    }


    public void addContacts(ObservableList<Person> contactList) {
        if(contactList.isEmpty())
            this.contactList.removeAll(this.contactList);
        else
            this.contactList.addAll(contactList);
    }

    public void setContactList(ObservableList<Person> contactList) {
        System.out.println(contactList.size());
        this.contactList = contactList;
    }
    public void addNewContact(Person newContact) {
        this.contactList.add(newContact);
    }
    public void deleteContact(int index) {
        contactList.remove(index);
    }
    public void setRecentFilePaths() {
        recentFilePaths = ioManager.read(ioManager.RECENT_OPEN_ABSOLUTE_PATH);
    }

    public void addRecentPath(String path) {
        recentFilePaths.remove(path);
        recentFilePaths.add(path);
        ioManager.saveStrData(ioManager.RECENT_OPEN_ABSOLUTE_PATH, recentFilePaths);
    }

    public List<String> getRecentFilePaths() {
        return recentFilePaths;
    }

    public boolean filterPredicate(Person contact) {

        boolean firstNameMatched = contact.getFirstname().toLowerCase().contains(searchedContact);
        boolean lastNameMatched = contact.getLastname().toLowerCase().contains(searchedContact);

        return firstNameMatched || lastNameMatched;
    }

    public void setFilterPredicate(String searchStr) {
        this.searchedContact = searchStr.toLowerCase();
        filteredContactList.setPredicate(this::filterPredicate);
    }

    public SortedList<Person> getSortedContactList() {
        return sortedContactList;
    }

    public void setIoManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

}
