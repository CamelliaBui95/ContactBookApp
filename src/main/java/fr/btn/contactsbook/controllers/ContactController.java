package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.utils.DateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private void initialize() {
        this.firstNameCol.setCellValueFactory(cell -> cell.getValue().firstnameProperty());
        this.lastNameCol.setCellValueFactory(cell -> cell.getValue().lastnameProperty());

        disableButtons();

        contactTable.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> showContactDetail(n));
        contactTable.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> enableButtons());
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
    }

    @FXML
    private void handleNewContact() {
        Person newPerson = new Person();
        boolean isOkClicked = contactsBookApp.showEditDialog("New Contact", newPerson);

        if(isOkClicked)
            contactList.add(newPerson);

        showContactDetail(newPerson);
    }

    public void setContactList(ObservableList<Person> contactList) {
        this.contactList = contactList;
        contactTable.setItems(this.contactList);
    }

    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }


    private void enableButtons() {
        this.editBtn.setDisable(false);
        this.deleteBtn.setDisable(false);
    }

    private void disableButtons() {
        this.editBtn.setDisable(true);
        this.deleteBtn.setDisable(true);
    }

}
