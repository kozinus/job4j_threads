package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.tasks = new SimpleBlockingQueue<>(size);
        this.threads = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            this.threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        this.tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
            this.threads.get(i).start();
        }
    }

    public void work(Runnable job) {
        try {
            this.tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        for (Thread thread : this.threads) {
            thread.interrupt();
        }
    }
}