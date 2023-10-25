package ru.job4j.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    private Cache cache;
    private Base first;
    private Base second;
    private Base third;
    private Base fourth;

    @BeforeEach
    public void init() {
        cache = new Cache();
        first = new Base(1, 0);
        second = new Base(2, 0);
        third = new Base(1, 0);
        fourth = new Base(1, 1);
    }

    @Test
    public void addingTest() {
        assertThat(cache.add(first)).isTrue();
        assertThat(cache.add(third)).isFalse();
    }

    @Test
    public void addingAndDeleteThenAdd() {
        cache.add(first);
        cache.add(second);
        cache.delete(first);
        assertThat(cache.add(third)).isTrue();
    }

    @Test
    public void addingAndThenUpdate() {
        cache.add(first);
        assertThat(cache.update(third)).isTrue();
    }

    @Test
    public void addingAndThenUpdateButWrongVersion() {
        cache.add(first);
        Assertions.assertThrows(OptimisticException.class, () -> cache.update(fourth));
    }
}