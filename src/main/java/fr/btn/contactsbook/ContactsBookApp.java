package fr.btn.contactsbook;

import fr.btn.contactsbook.controllers.ContactController;
import fr.btn.contactsbook.controllers.ContactNewEditDialogController;
import fr.btn.contactsbook.controllers.MenuController;
import fr.btn.contactsbook.dao.ContactDAO;
import fr.btn.contactsbook.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ContactsBookApp extends Application {
    private static final String APP_NAME = "Contact Book Application";
    private Stage primaryStage;
    private BorderPane mainWindow;
    private ObservableList<Person> contactList;
    private File contactFile;
    private ContactDAO contactDAO;
    public ContactsBookApp() {

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME);

        initMainWindow();
    }

    public void initMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ContactsBookApp.class.getResource("view/ContactsBook-View.fxml"));
            mainWindow = (BorderPane) loader.load();

            MenuController controller = loader.getController();
            controller.setContactsBookApp(this);

            Scene scene = new Scene(mainWindow);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void showContactsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ContactsBookApp.class.getResource("view/Contacts-View.fxml"));
            AnchorPane contactsView = (AnchorPane) loader.load();
            ContactController controller = loader.getController();

            controller.setContactsBookApp(this);
            controller.setContactList(this.contactList);

            this.primaryStage.setTitle(APP_NAME + " - " + this.contactFile.getName());
            mainWindow.setCenter(contactsView);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showEditDialog(String dialogTitle, Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ContactsBookApp.class.getResource("view/ContactNewEditDialog-View.fxml"));
            AnchorPane dialogBox = loader.load();
            ContactNewEditDialogController controller = loader.getController();

            Scene scene = new Scene(dialogBox);
            Stage dialogStage = new Stage();

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.setTitle(dialogTitle);
            dialogStage.setScene(scene);

            controller.setDialogStage(dialogStage);
            controller.setContact(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setContactFile(File contactFile) {
        this.contactFile = contactFile;
        setContactList();
    }

    public void setContactList() {

        this.contactDAO = new ContactDAO(this.contactFile);
        ObservableList<Person> contactList = contactDAO.read();

        if(contactList != null)
            this.contactList = contactList;
        else
            this.contactList = FXCollections.observableArrayList();
    }

    public ContactDAO getContactDAO() {
        return contactDAO;
    }

    public ObservableList<Person> getContactList() {
        return this.contactList;
    }

    public BorderPane getMainWindow() {
        return mainWindow;
    }
}

//"C:/Users"
//new File(path).mkdir()