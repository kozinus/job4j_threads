package ru.job4j.pools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {
    private int[][] matrix;

    @BeforeEach
    public void init() {
        matrix = new int[][]{{1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}};
    }

    @Test
    public void findSumsLinear() {
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        assertThat(sums[0].getRowSum()).isEqualTo(15);
    }

    @Test
    public void findSumsAsync() {
        RowColSum.Sums[] sums;
        try {
            sums = RowColSum.asyncSum(matrix);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        assertThat(sums[0].getColSum()).isEqualTo(55);
    }

}