package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileName);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[128];
            int bytesRead;
            int totalBytesRead = 0;
            int downloadOffset = 1000000;
            var downloadAt = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                long downloadTime = System.nanoTime() - downloadAt;
                System.out.println("Read 128 bytes : " + downloadTime + " nano.");
                downloadOffset -= downloadTime;
                if (totalBytesRead > speed && downloadOffset > 0) {
                    try {
                        System.out.println("Pause: " + downloadOffset + " nano.");
                        Thread.sleep(0, downloadOffset);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    totalBytesRead = 0;
                    downloadOffset = 1000000;
                }
                downloadAt = System.nanoTime();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];

        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException();
        }
        if (speed <= 0) {
            throw new IllegalArgumentException();
        }
        if ("".equals(fileName) || !fileName.contains(".")
                || fileName.startsWith(".") || fileName.endsWith(".")) {
            throw new IllegalArgumentException();
        }
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}