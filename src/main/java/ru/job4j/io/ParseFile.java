package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) {
        synchronized (file) {
            String output = "";
            try (InputStream i = new FileInputStream(file);
                 BufferedInputStream in = new BufferedInputStream(i)) {
                int data;
                while ((data = i.read()) > 0) {
                    if (filter.test(data)) {
                        output += (char) data;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }
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