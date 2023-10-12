package fr.btn.contactsbook.services.dao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
    private File file;

    public TextFile(File file) {
        setTextFile(file);
    }
    public List<String> read() {
        List<String> texts = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String newLine = reader.readLine();
            while(newLine != null) {
                texts.add(newLine);
                newLine = reader.readLine();
            }

            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return texts;
    }

    public boolean writeAll(List<String> texts) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            int i = 0;
            while(i < texts.size())
                write(texts.get(i++), writer);

            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void write(String text, BufferedWriter writer) throws IOException {
        writer.append(text);
        writer.newLine();
    }
    public static File createFile(String pathName) {
        if(!hasFile(pathName))
            return new File(pathName);

        return null;
    }

    private static boolean hasFile(String pathName) {
        return Files.exists(Path.of(pathName));
    }

    public File getFile() {
        return this.file;
    }

    public void setTextFile(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.file = file;
    }

}

// new File("chemin/name");