package ru.job4j.concurrent;

import java.io.*;

public class SaveInFile {
    private final File file;

    public SaveInFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (file) {
            try (OutputStream o = new FileOutputStream(file);
                 BufferedOutputStream out = new BufferedOutputStream(o)) {
                for (int i = 0; i < content.length(); i += 1) {
                    out.write(content.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
