package fr.btn.contactsbook.services;

import fr.btn.contactsbook.ContactsBookApp;
import fr.btn.contactsbook.services.dao.ContactDAO;
import fr.btn.contactsbook.services.dao.TextFile;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IOManager {
    //private final String HOME = System.getProperty("user.home");
    private final String CONTACT_DIR_PATH = "/contactBookApp";
    private final String RECENT_DIR_PATH = "/recentPaths";
    private final String CONTACT_FILE_NAME = "myContacts";
    private final String RECENT_FILE_NAME = "recentlyOpen";
    private final String EXTENSION = ".txt";
    public final String CONTACT_ABSOLUTE_PATH = CONTACT_DIR_PATH + "/" + CONTACT_FILE_NAME;
    public final String RECENT_OPEN_ABSOLUTE_PATH = RECENT_DIR_PATH + "/" + RECENT_FILE_NAME + EXTENSION;
    private int index = 0;
    private RepositoryManager repositoryManager;
    private ContactDAO contactDAO;
    private Stage window;
    private FileChooser fileChooser;
    private File contactFile;
    private File recentPathsFile;
    private static IOManager ioManager;
    private IOManager(ContactsBookApp contactsBookApp) {
        if(!hasDir(CONTACT_DIR_PATH))
            new File(CONTACT_DIR_PATH).mkdir();

        if(!hasDir(RECENT_DIR_PATH))
            new File(RECENT_DIR_PATH).mkdir();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt","*.txt"));
        fileChooser.setInitialDirectory(new File(CONTACT_DIR_PATH));

        this.contactDAO = new ContactDAO();
        this.window = contactsBookApp.getPrimaryStage();
    }
    public static IOManager getInstance(ContactsBookApp contactsBookApp) {
        if(ioManager == null)
            ioManager = new IOManager(contactsBookApp);
        return ioManager;
    }

    public File showOpenDialog() {
        File selectedFile = fileChooser.showOpenDialog(this.window);
        if(selectedFile == null)
            return null;

        this.contactFile = selectedFile;
        contactDAO.setTextFile(selectedFile);
        repositoryManager.setContactList(contactDAO.read());

        return selectedFile;
    }
    public File createNewFile() {
        File newFile = null;
        while(newFile == null) {
            String fileName = index == 0 ? CONTACT_ABSOLUTE_PATH + EXTENSION : CONTACT_ABSOLUTE_PATH + "(" + index + ")" + EXTENSION;
            newFile = createFile(fileName);
            index++;
        }

        this.contactFile = newFile;
        contactDAO.setTextFile(newFile);
        repositoryManager.setContactList(contactDAO.read());
        return newFile;
    }
    public void saveContactFile() {
        contactDAO.save(repositoryManager.getContactList());
    }
    public File saveFileAs() {
        return fileChooser.showSaveDialog(this.window);
    }
    public File saveContactFileAs() {
        File savedFile = saveFileAs();
        if(savedFile == null) return null;

        this.contactFile = savedFile;
        contactDAO.setTextFile(savedFile);
        contactDAO.save(repositoryManager.getContactList());

        return savedFile;
    }
    private boolean hasDir(String dirPath) {
        return Files.exists(Path.of(dirPath));
    }

    public void submitFile(File file) {
        this.contactFile = file;
        contactDAO.setTextFile(file);
        repositoryManager.setContactList(contactDAO.read());
    }
    public File createFile(String pathName) {
        if(!hasFile(pathName))
            return new File(pathName);

        return null;
    }
    private static boolean hasFile(String pathName) {
        return Files.exists(Path.of(pathName));
    }

    public File getContactFile() {
        return contactFile;
    }

    public void setRepositoryManager(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

}

/*

* */