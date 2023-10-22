package fr.btn.contactsbook;

import fr.btn.contactsbook.controllers.ContactController;
import fr.btn.contactsbook.controllers.FileMenuController;
import fr.btn.contactsbook.controllers.ContactNewEditDialogController;
import fr.btn.contactsbook.services.IOManager;
import fr.btn.contactsbook.services.RepositoryManager;
import fr.btn.contactsbook.model.Person;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/*public class ContactsBookApp extends Application {
    private static final String APP_NAME = "Contact Book Application";
    private Stage primaryStage;
    private BorderPane mainWindow;
    private ObservableList<Person> contactList;
    private File contactFile;
    private ContactDAO contactDAO;

    private IOManager ioManager;
    public ContactsBookApp() {
        contactDAO = new ContactDAO();

        ioManager = IOManager.getInstance();
        ioManager.setContactDAO(contactDAO);
        ioManager.setApp(this);
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
            loader.setLocation(ContactsBookApp.class.getResource("view/MainMenu-View.fxml"));

            mainWindow = (BorderPane) loader.load();

            FileMenuController controller = loader.getController();
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
        this.contactDAO.setTextFile(this.contactFile);
    }
    public void setContactList() {
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
}*/
public class ContactsBookApp extends Application {
    private static final String APP_NAME = "Contact Book Application";
    private Stage primaryStage;
    private BorderPane mainMenu;

    private FileMenuController fileMenuController;
    private RepositoryManager repositoryManager;

    private IOManager ioManager;

    public ContactsBookApp() {
       ioManager = IOManager.getInstance(this);
       repositoryManager = RepositoryManager.getInstance();
       repositoryManager.setIoManager(ioManager);
       repositoryManager.setUpRepository();
       ioManager.setRepositoryManager(repositoryManager);

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME);

        initMainMenu();
    }

    public void initMainMenu() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainMenu-View.fxml"));
           mainMenu = loader.load();
           FileMenuController controller = loader.getController();
           controller.setContactsBookApp(this);
           controller.setRepositoryManager(repositoryManager);
           controller.setIoManager(ioManager);
           fileMenuController = controller;
           primaryStage.setScene(new Scene(mainMenu));
           primaryStage.show();
       } catch(IOException e) {
           e.printStackTrace();
       }

    }

    public void showContactView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Contacts-View.fxml"));
            AnchorPane contactsView = loader.load();

            ContactController controller = loader.getController();
            controller.setContactsBookApp(this);
            controller.setFileMenuController(fileMenuController);
            controller.setRepositoryManager(repositoryManager);

            mainMenu.setCenter(contactsView);
            primaryStage.setTitle(APP_NAME + " - " + ioManager.getContactFile().getName());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void closeContactView() {
        this.primaryStage.setTitle(APP_NAME);
        mainMenu.setCenter(null);
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

    public void showAlertDialogOnClose(String header) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation On Saving Data");
        alert.setHeaderText(header);
        alert.setContentText("Do you want to save all contact data before closing ?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            ioManager.saveAll();
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    public void stop() {
        if(!this.fileMenuController.isSaved())
            showAlertDialogOnClose("You are closing application.");
    }

}
