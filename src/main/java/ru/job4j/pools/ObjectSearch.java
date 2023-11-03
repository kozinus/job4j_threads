package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ObjectSearch extends RecursiveTask<Object> {
    private final Object[] objects;
    private final int from;
    private final int to;
    private final Object obj;

    public ObjectSearch(Object[] objects, int from, int to, Object obj) {
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
    protected Object compute() {
        int out = -1;
        if (to - from < 10) {
            out = linearSearch();
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
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (int) forkJoinPool.invoke(new ObjectSearch(objects, 0, objects.length - 1, obj));
    }
}
