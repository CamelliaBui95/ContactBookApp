package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
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
    private ContactsBookApp contactsBookApp;
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

    public MenuController() {
        fileChooser = new FileChooser();
    }
    @FXML
    private void initialize() {

    }
    @FXML
    private void handleOpenFile() {

    }

    @FXML
    private void handleNewFile() {
        if(!hasContactDir())
    }
    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
        this.primaryStage = contactsBookApp.getPrimaryStage();
    }

    private boolean hasContactDir() {
       return Files.exists(Path.of("C:/Users/contactBookApp"));
    }

}

//"C:/Users"
//new File(path).mkdir()
