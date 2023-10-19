package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Object monitor = this;

    private final Queue<T> queue = new LinkedList<>();

    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= size) {
            monitor.wait();
        }
        queue.offer(value);
        monitor.notifyAll();
    }

    public synchronized boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public synchronized T poll() throws InterruptedException {
        while (this.isEmpty()) {
            monitor.wait();
        }
        T value = this.queue.poll();
        monitor.notifyAll();
        return value;
    }
}