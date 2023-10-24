package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        int temp;
        do {
            ref = count.get();
            temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        return count.get();
    }
}
