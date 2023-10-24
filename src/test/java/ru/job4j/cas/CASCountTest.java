package ru.job4j.cas;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    public void fifthTeenIncrements() {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
            }
        });
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertThat(count.get()).isEqualTo(15);
    }

    @Test
    public void twentyIncrements() {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                count.increment();
            }
        });
        Thread two = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                count.increment();
                count.increment();
            }
        });
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertThat(count.get()).isEqualTo(20);
    }

}