package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 101; i++) {
                            Thread.sleep(1000);
                            System.out.println("\rLoading: " + i + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                );
        thread.start();
        System.out.println("Main");
    }
}

