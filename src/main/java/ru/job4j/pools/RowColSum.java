package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
            }
            for (int[] ints : matrix) {
                colSum += ints[i];
            }
            sums[i] = new Sums();
            sums[i].setRowSum(rowSum);
            sums[i].setColSum(colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Sums[] sums = new Sums[matrix.length];
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            futures.add(getRowTask(matrix, i));
            futures.add(getColTask(matrix, i));
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i].setRowSum(futures.get(i).get());
            sums[i].setColSum(futures.get(i + 1).get());
        }
        return sums;
    }

    public static CompletableFuture<Integer> getRowTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[index][i];
            }
            return rowSum;
        });
    }

    public static CompletableFuture<Integer> getColTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int colSum = 0;
            for (int[] ints : matrix) {
                colSum += ints[index];
            }
            return colSum;
        });
    }
}