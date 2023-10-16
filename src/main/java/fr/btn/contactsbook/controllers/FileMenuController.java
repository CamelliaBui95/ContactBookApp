package fr.btn.contactsbook.controllers;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.services.IOManager;
import fr.btn.contactsbook.services.RepositoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileMenuController {
    private ContactsBookApp contactsBookApp;
    private IOManager ioManager;
    private RepositoryManager repositoryManager;
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
    @FXML
    private MenuItem closeItem;
    private boolean isSaved = true;

    public FileMenuController() {

    }

    @FXML
    private void initialize() {
        saveItem.setDisable(isSaved);
        saveAsItem.setDisable(isSaved);
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
        File newFile = ioManager.createNewContactFile();

        updateRecentPaths(newFile);
        contactsBookApp.showContactsView();
        toggleSave();
    }
    @FXML
    private void handleSaveFile() {
        ioManager.saveContactFile();
        toggleSave();
    }
    @FXML
    private void handleSaveFileAs() {
        File savedFile = ioManager.saveContactFileAs();
        if(savedFile == null) return;

        ioManager.submitFile(savedFile);
        contactsBookApp.showContactsView();
        updateRecentPaths(savedFile);
    }
    @FXML
    private void openRecentFile(String path) {
        File recentFile = ioManager.getFile(path);
        ioManager.submitFile(recentFile);
        contactsBookApp.showContactsView();
        updateRecentPaths(recentFile);
    }
    private void setRecentPathsAsMenuItems() {
        if(repositoryManager.getRecentFilePaths().isEmpty()) {
            openRecent.setDisable(true);
            return;
        }

        openRecent.setDisable(false);
        List<MenuItem> menuItems = new ArrayList<>();
        int i = repositoryManager.getRecentFilePaths().size() - 1;

        while(i >= 0) {
            String path = repositoryManager.getRecentFilePaths().get(i--);
            MenuItem menuItem = new MenuItem(path);
            menuItem.setOnAction(e -> openRecentFile(path));
            menuItems.add(menuItem);
        }

        openRecent.getItems().setAll(menuItems);
    }

    private void updateRecentPaths(File file) {
        repositoryManager.addRecentPath(file.getAbsolutePath());
        setRecentPathsAsMenuItems();
    }

    @FXML
    public void handleOnClose() {
        if(!this.isSaved)
            contactsBookApp.showAlertDialogOnClose("You are closing this contact sheet.");
        contactsBookApp.closeContactView();
        toggleSave();
    }

    public void toggleSave() {
        this.isSaved = !isSaved;

        saveItem.setDisable(isSaved);
        saveAsItem.setDisable(isSaved);
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    public void setIoManager(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
        setRecentPathsAsMenuItems();

    }

}
