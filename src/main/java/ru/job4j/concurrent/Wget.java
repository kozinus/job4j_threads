package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 101; i++) {
                            Thread.sleep(1000);
                            System.out.print("\r Loading: " + i + "%");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                );
        thread.start();
        System.out.println("Main");
    }
}

