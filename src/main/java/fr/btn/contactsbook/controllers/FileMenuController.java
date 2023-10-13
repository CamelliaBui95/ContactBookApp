package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.services.IOManager;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileMenuController {
    private ContactsBookApp contactsBookApp;
    private IOManager ioManager;
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
    @FXML
    private Menu openRecent;
    private List<String> recentPaths = new ArrayList<>();
    public FileMenuController() {

    }
    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }
    @FXML
    private void handleOpenFile() {
        File selectedFile = ioManager.showOpenDialog();
        if(selectedFile == null) return;

        updateRecentPaths(selectedFile);
        contactsBookApp.showContactsView();
    }
    @FXML
    private void handleNewFile() {
        File newFile = ioManager.createNewFile();

        updateRecentPaths(newFile);
        contactsBookApp.showContactsView();
    }
    @FXML
    private void handleSaveFile() {
        ioManager.saveContactFile();
    }
    @FXML
    private void handleSaveFileAs() {
        File savedFile = ioManager.saveContactFileAs();
        if(savedFile == null) return;

        contactsBookApp.showContactsView();
    }
    @FXML
    private void openRecentFile(String path) {
        File recentFile = ioManager.createFile(path);
        ioManager.submitFile(recentFile);
        updateRecentPaths(recentFile);
        contactsBookApp.showContactsView();
    }

    private void setRecentPathsAsMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        int i = recentPaths.size() - 1;

        while(i >= 0) {
            String path = recentPaths.get(i--);
            MenuItem menuItem = new MenuItem(path);
            menuItem.setOnAction(e -> openRecentFile(path));
            menuItems.add(menuItem);
        }

        openRecent.getItems().setAll(menuItems);
    }
    private void savePath(String path) {
        if(recentPaths.contains(path))
            recentPaths.remove(path);

        recentPaths.add(path);
    }
    private void updateRecentPaths(File file) {
        savePath(file.getAbsolutePath());
        setRecentPathsAsMenuItems();
    }

    public void setIoManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

}
