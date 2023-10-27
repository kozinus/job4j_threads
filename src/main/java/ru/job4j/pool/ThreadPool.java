package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final static int SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
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

    public void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : this.threads) {
            thread.interrupt();
        }
    }
}