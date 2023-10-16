package fr.btn.contactsbook.services;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.List;

public class RepositoryManager {
    private ObservableList<Person> contactList;
    private FilteredList<Person> filteredContactList;
    private SortedList<Person> sortedContactList;
    private List<String> recentFilePaths;
    private ContactsBookApp contactsBookApp;
    private IOManager ioManager;
    private String searchedContact;

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
        contactList = FXCollections.observableArrayList();
        /*filteredContactList = new FilteredList<>(contactList, null);
        sortedContactList = new SortedList<>(filteredContactList);*/

        setRecentFilePaths();
    }

    public SortedList<Person> getSortedContactList() {
        return sortedContactList;
    }

    public FilteredList<Person> getFilteredList() {
        return filteredContactList;
    }

    public ObservableList<Person> getContactList() {
        return contactList;
    }
    public void setContactList(ObservableList<Person> contactList) {
        this.contactList = contactList;
        filteredContactList = new FilteredList<>(this.contactList, null);
        sortedContactList = new SortedList<>(filteredContactList);
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

    public void setFilerPredicate(String searchStr) {
        this.searchedContact = searchStr.toLowerCase();
        filteredContactList.setPredicate(this::filterPredicate);
    }

    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }
    public void setIoManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

}
