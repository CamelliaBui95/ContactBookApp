package fr.btn.contactsbook;

import fr.btn.contactsbook.controllers.ContactController;
import fr.btn.contactsbook.controllers.ContactNewEditDialogController;
import fr.btn.contactsbook.controllers.MenuController;
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
import java.nio.file.Files;
import java.nio.file.Path;

public class ContactsBookApp extends Application {
    private Stage primaryStage;
    private BorderPane mainWindow;
    private ObservableList<Person> contacts = FXCollections.observableArrayList();
    private File contactFile;
    public ContactsBookApp() {
        contacts.add(new Person("Hans", "Muster"));
        contacts.add(new Person("Ruth", "Mueller"));
        contacts.add(new Person("Heinz", "Kurz"));
        contacts.add(new Person("Cornelia", "Meier"));
        contacts.add(new Person("Werner", "Meyer"));
        contacts.add(new Person("Lydia", "Kunz"));
        contacts.add(new Person("Anna", "Best"));
        contacts.add(new Person("Stefan", "Meier"));
        contacts.add(new Person("Martin", "Mueller"));
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Contact Book Application");

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

            controller.setContactList(contacts);
            controller.setContactsBookApp(this);

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
    }

    public BorderPane getMainWindow() {
        return mainWindow;
    }
}

//"C:/Users"
//new File(path).mkdir()