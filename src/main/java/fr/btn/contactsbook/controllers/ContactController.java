package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.services.RepositoryManager;
import fr.btn.contactsbook.utils.DateUtil;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ContactController {
    @FXML
    private TableView<Person> contactTable;
    @FXML
    private TableColumn<Person, String> firstNameCol;
    @FXML
    private TableColumn<Person, String> lastNameCol;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    private ObservableList<Person> contactList;
    private ContactsBookApp contactsBookApp;
    private RepositoryManager repositoryManager;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField searchField;
    private FileMenuController fileMenuController;
    @FXML
    private void initialize() {
        this.firstNameCol.setCellValueFactory(cell -> cell.getValue().firstnameProperty());
        this.lastNameCol.setCellValueFactory(cell -> cell.getValue().lastnameProperty());

        disableButtons();

        contactTable.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> showContactDetail(n));
        contactTable.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> enableButtons());

        searchField.textProperty().addListener((ob, o, n) -> repositoryManager.setFilerPredicate(n));
    }
    private void showContactDetail(Person person) {
        if(person == null)
            return;

        firstNameLabel.setText(person.getFirstname());
        lastNameLabel.setText(person.getLastname());
        birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        streetLabel.setText(person.getStreet());
        cityLabel.setText(person.getCity());
        postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
    }
    @FXML
    private void handleDeleteContact() {
        int index = contactTable.getSelectionModel().getSelectedIndex();
        if(index == -1) return;

        contactList.remove(index);
    }

    @FXML
    private void handleEditContact() {
        Person selectedContact = contactTable.getSelectionModel().getSelectedItem();
        if(selectedContact == null)
            return;

        contactsBookApp.showEditDialog("Edit Contact", selectedContact);
        showContactDetail(selectedContact);
        fileMenuController.toggleSave();
    }

    @FXML
    private void handleNewContact() {
        Person newPerson = new Person();
        boolean isOkClicked = contactsBookApp.showEditDialog("New Contact", newPerson);

        if(isOkClicked) {
            contactList.add(newPerson);
            fileMenuController.toggleSave();
        }

        showContactDetail(newPerson);
    }

    private void enableButtons() {
        this.editBtn.setDisable(false);
        this.deleteBtn.setDisable(false);
    }

    private void disableButtons() {
        this.editBtn.setDisable(true);
        this.deleteBtn.setDisable(true);
    }

    public void setContactList(ObservableList<Person> contactList) {
        this.contactList = contactList;
    }

    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
        setContactList(this.repositoryManager.getContactList());

        SortedList<Person> sortedList = this.repositoryManager.getSortedContactList();
        sortedList.comparatorProperty().bind(contactTable.comparatorProperty());
        contactTable.setItems(sortedList);
    }

    public void setFileMenuController(FileMenuController fileMenuController) {
        this.fileMenuController = fileMenuController;
    }

}
