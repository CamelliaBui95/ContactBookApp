package fr.btn.contactsbook.services;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.services.dao.ContactDAO;
import fr.btn.contactsbook.services.dao.TextFile;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOManager {
    private final String HOME = System.getProperty("user.home");
    private final String DIR_PATH = "/contactBookApp";
    private final String FILE_NAME = "contacts";
    private final String EXTENTION = ".txt";
    private final String ABSOLUTE_PATH = DIR_PATH + "/" + FILE_NAME;
    private int index = 0;
    private ContactsBookApp contactsBookApp;
    private ContactDAO contactDAO;
    private Stage window;
    private FileChooser fileChooser;
    private static IOManager ioManager;
    private IOManager() {
        if(!hasContactDir())
            new File(DIR_PATH).mkdir();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt","*.txt"));
        fileChooser.setInitialDirectory(new File(DIR_PATH));
    }
    public static IOManager getInstance() {
        if(ioManager == null)
            ioManager = new IOManager();
        return ioManager;
    }
    public File showOpenDialog() {
        File selectedFile = fileChooser.showOpenDialog(this.window);
        if(selectedFile == null)
            return null;

        contactsBookApp.setContactFile(selectedFile);
        contactsBookApp.setContactList();

        return selectedFile;
    }
    public File createNewFile() {
        File newFile = null;
        while(newFile == null) {
            String fileName = index == 0 ? ABSOLUTE_PATH + EXTENTION : ABSOLUTE_PATH + "(" + index + ")" + EXTENTION;
            newFile = TextFile.createFile(fileName);
            index++;
        }

        contactsBookApp.setContactFile(newFile);
        contactsBookApp.setContactList();

        return newFile;
    }
    public void saveFile() {
        this.contactDAO.save(this.contactsBookApp.getContactList());
    }
    public void saveFileAs() {
        File savedFile = fileChooser.showSaveDialog(this.window);
        if(savedFile == null)
            return;

        this.contactDAO.setTextFile(savedFile);
        saveFile();
    }
    public void setContactDAO(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }
    public void setApp(ContactsBookApp contactsBookApp) {
        this.contactsBookApp = contactsBookApp;
        this.window = contactsBookApp.getPrimaryStage();
    }
    private boolean hasContactDir() {
        return Files.exists(Path.of(DIR_PATH));
    }
    private static boolean hasFile(String pathName) {
        return Files.exists(Path.of(pathName));
    }
    public File createFileObj(String pathName) {
        return new File(pathName);
    }

    public void submitFile(File file) {
        contactsBookApp.setContactFile(file);
        contactsBookApp.setContactList();
    }

}
