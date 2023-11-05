package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Sums[] sums = new Sums[matrix.length];
        List<CompletableFuture<Integer[]>> futures = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            futures.add(getTask(matrix, i));
        }
        for (int i = 0; i < matrix.length; i++) {
            Integer[] task = futures.get(i).get();
            sums[i].setRowSum(task[0]);
            sums[i].setColSum(task[1]);
        }
        return sums;
    }

    public static CompletableFuture<Integer[]> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[index][i];
                colSum += matrix[i][index];
            }
            return new Integer[]{rowSum, colSum};
        });
    }
}