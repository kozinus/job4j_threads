package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(10);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "producer");
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 17; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }, "consumer");
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < 3; i++) {
            set.add(queue.poll());
        }
        assertThat(set).hasSize(3).containsAll(Set.of(17, 18, 19));
    }
}