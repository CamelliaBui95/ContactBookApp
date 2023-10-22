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
    private Menu openRecentItem;
    @FXML
    private MenuItem closeItem;
    private boolean isSaved = true;

    public FileMenuController() {

    }

    @FXML
    private void initialize() {
        toggleOffSave();
    }


    @FXML
    private void handleOpenFile() {
        File selectedFile = ioManager.showOpenDialog();
        if(selectedFile == null) return;

        toggleOffSave();
        updateRecentPaths(selectedFile);
        contactsBookApp.showContactView();
    }
    @FXML
    private void handleNewFile() {
        File newFile = ioManager.createNewContactFile();

        updateRecentPaths(newFile);
        contactsBookApp.showContactView();
        toggleOnSave();
    }
    @FXML
    private void handleSaveFile() {
        ioManager.saveContactFile();
        toggleOffSave();
    }
    @FXML
    private void handleSaveFileAs() {
        File savedFile = ioManager.saveContactFileAs();
        if(savedFile == null) return;

        ioManager.submitFile(savedFile);
        contactsBookApp.showContactView();
        updateRecentPaths(savedFile);
    }
    @FXML
    private void openRecentFile(String path) {
        File recentFile = ioManager.getFile(path);
        ioManager.submitFile(recentFile);
        contactsBookApp.showContactView();
        updateRecentPaths(recentFile);
    }

    @FXML
    public void handleOnClose() {
        if(!this.isSaved)
            contactsBookApp.showAlertDialogOnClose("You are closing this contact sheet.");
        contactsBookApp.closeContactView();
        toggleOffSave();
    }

    @FXML
    public void handleOnExit() {
        contactsBookApp.stop();
    }

    private void setRecentPathsAsMenuItems() {
        if(repositoryManager.getRecentFilePaths().isEmpty()) {
            openRecentItem.setDisable(true);
            return;
        }

        openRecentItem.setDisable(false);
        List<MenuItem> menuItems = new ArrayList<>();
        int i = repositoryManager.getRecentFilePaths().size() - 1;

        while(i >= 0) {
            String path = repositoryManager.getRecentFilePaths().get(i--);
            MenuItem menuItem = new MenuItem(path);
            menuItem.setOnAction(e -> openRecentFile(path));
            menuItems.add(menuItem);
        }

        openRecentItem.getItems().setAll(menuItems);
    }

    private void updateRecentPaths(File file) {
        repositoryManager.addRecentPath(file.getAbsolutePath());
        setRecentPathsAsMenuItems();
    }



    public void toggleOffSave() {
        this.isSaved = true;

        saveItem.setDisable(isSaved);
        saveAsItem.setDisable(isSaved);
    }

    public void toggleOnSave() {
        this.isSaved = false;

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

    public void setContactsBookApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
    }

}
