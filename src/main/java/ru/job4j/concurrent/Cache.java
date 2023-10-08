package ru.job4j.concurrent;

public final class Cache {
    private Cache cache;

    public synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}