package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ObjectSearch<T> extends RecursiveTask<Integer> {
    private final T[] objects;
    private final int from;
    private final int to;
    private final T obj;

    public ObjectSearch(T[] objects, int from, int to, T obj) {
        this.objects = objects;
        this.from = from;
        this.to = to;
        this.obj = obj;
    }

    private int linearSearch() {
        int out = -1;
        for (int i = from; i <= to; i++) {
            if (objects[i] != null && objects[i].equals(obj)) {
                out = i;
                break;
            }
        }
        return out;
    }

    @Override
    protected Integer compute() {
        int out = -1;
        if (to == from) {
            if (objects[to] != null && objects[to].equals(obj)) {
                out = to;
            }
        } else {
            int mid = (from + to) / 2;
            ObjectSearch leftSearch = new ObjectSearch(objects, from, mid, obj);
            ObjectSearch rightSearch = new ObjectSearch(objects, mid + 1, to, obj);
            leftSearch.fork();
            rightSearch.fork();
            int left = (int) leftSearch.join();
            int right = (int) rightSearch.join();
            if (left != -1) {
                out = left;
            } else if (right != -1) {
                out = right;
            }
        }
        return out;
    }

    public static int find(Object[] objects, Object obj) {
        int out;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ObjectSearch search = new ObjectSearch(objects, 0, objects.length - 1, obj);
        if (objects.length < 10) {
            out = search.linearSearch();
        } else {
            out = (int) forkJoinPool.invoke(new ObjectSearch(objects, 0, objects.length - 1, obj));
        }
        return out;
    }
}
