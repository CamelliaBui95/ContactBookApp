package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.dao.ContactDAO;
import fr.btn.contactsbook.dao.TextFile;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class MenuController {
    private final String DIR_PATH = "/contactBookApp";
    private final String FILE_NAME = "contacts";
    private final String EXTENTION = ".txt";
    private final String ABSOLUTE_PATH = DIR_PATH + "/" + FILE_NAME;
    private ContactsBookApp contactsBookApp;
    private ContactDAO contactDAO;
    private FileChooser fileChooser;
    private Stage primaryStage;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem newItem;
    @FXML
    private MenuItem exitItem;
    private int index = 0;

    public MenuController() {
        if(!hasContactDir())
            new File(DIR_PATH).mkdir();

        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(DIR_PATH));
    }
    @FXML
    private void initialize() {

    }
    @FXML
    private void handleOpenFile() {
        File selectedFile = fileChooser.showOpenDialog(this.primaryStage);

        contactsBookApp.setContactFile(selectedFile);
        this.contactDAO = contactsBookApp.getContactDAO();
        contactsBookApp.showContactsView();
    }
    @FXML
    private void handleNewFile() {
        File newFile = null;
        while(newFile == null) {
            String fileName = index == 0 ? ABSOLUTE_PATH + EXTENTION : ABSOLUTE_PATH + "(" + index + ")" + EXTENTION;
            newFile = TextFile.createFile(fileName);
            index++;
        }

        contactsBookApp.setContactFile(newFile);
        this.contactDAO = contactsBookApp.getContactDAO();
        contactsBookApp.showContactsView();
    }
    @FXML
    private void handleSaveFile() {
        this.contactDAO.save(this.contactsBookApp.getContactList());
    }
    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
        this.primaryStage = contactsBookApp.getPrimaryStage();
    }

    private boolean hasContactDir() {
       return Files.exists(Path.of(DIR_PATH));
    }

    private void setContactDAO(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }
}

//"C:/Users"
//new File(path).mkdir()
