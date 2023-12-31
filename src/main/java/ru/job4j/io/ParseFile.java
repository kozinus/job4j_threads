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
            StringBuilder output = new StringBuilder();
            try (InputStream i = new FileInputStream(file);
                 BufferedInputStream in = new BufferedInputStream(i)) {
                int data;
                while ((data = in.read()) != -1) {
                    if (filter.test(data)) {
                        output.append((char) data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public String getContentIfTrue() {
        return getContent(x -> true);
    }

    public String getContentLowerThan0x80() {
        return getContent(x -> x < 0x80);
    }
}