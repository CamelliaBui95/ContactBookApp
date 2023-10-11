package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.model.Person;
import fr.btn.contactsbook.utils.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContactNewEditDialogController {
    private Stage dialogStage;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private Button submitBtn;
    private boolean okClicked;
    private Person contact;

    @FXML
    private void initialize() {
        submitBtn.setDisable(!isDataValid());

        firstNameField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
        lastNameField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
        birthdayField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
        streetField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
        cityField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
        postalCodeField.textProperty().addListener((obs, o, n) -> submitBtn.setDisable(!isDataValid()));
    }

    @FXML
    private void handleOnCancel() {
        dialogStage.close();
    }
    @FXML
    private void handleSubmitContact() {
        if(!isDataValid()) return;

        contact.setFirstname(firstNameField.getText());
        contact.setLastname(lastNameField.getText());
        contact.setBirthday(DateUtil.parse(birthdayField.getText()));
        contact.setStreet(streetField.getText());
        contact.setCity(cityField.getText());
        contact.setPostalCode(postalCodeField.getText());

        okClicked = true;
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isDataValid() {
        boolean isFirstNameValid = firstNameField.getText() != null && !firstNameField.getText().isEmpty();
        boolean isLastNameValid = lastNameField.getText() != null && !lastNameField.getText().isEmpty();
        boolean isBirthdayValid = birthdayField.getText() != null && DateUtil.isValidDate(birthdayField.getText());
        boolean isStreetValid = streetField.getText() != null && !streetField.getText().isEmpty();
        boolean isCityValid = cityField.getText() != null && !cityField.getText().isEmpty();
        boolean isPostalCodeValid = postalCodeField.getText() != null && !postalCodeField.getText().isEmpty() && postalCodeField.getText().length() >= 5 && postalCodeField.getText().length() <= 7;

        return isFirstNameValid && isLastNameValid && isBirthdayValid && isStreetValid && isCityValid && isPostalCodeValid;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setContact(Person person) {
        if(person == null)
            return;

        this.contact = person;

        firstNameField.setText(person.getFirstname());
        lastNameField.setText(person.getLastname());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        streetField.setText(person.getStreet());
        cityField.setText(person.getCity());

        String postalCodeStr = person.getPostalCode() == 0 ? "" : Integer.toString(person.getPostalCode());
        postalCodeField.setText(postalCodeStr);
    }

}
