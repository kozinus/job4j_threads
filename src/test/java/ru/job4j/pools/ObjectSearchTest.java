package ru.job4j.pools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectSearchTest {
    private Object[] objects;

    @BeforeEach
    public void init() {
        objects = new Object[] {new Object(), 4, "word", "word1", null, new ArrayList<>(), "words", 12, new Object(), new User("Kevin", "email"), 5, "Kolya", new EmailNotitfication()};
    }

    @Test
    public void ifIntInArrayRecursive() {
        assertThat(ObjectSearch.find(objects, 4)).isEqualTo(1);
    }

    @Test
    public void ifIntNotInArrayRecursive() {
        assertThat(ObjectSearch.find(objects, 41)).isEqualTo(-1);
    }

    @Test
    public void ifStringInArrayRecursive() {
        assertThat(ObjectSearch.find(objects, "Kolya")).isEqualTo(11);
    }

    @Test
    public void ifUserInArrayRecursive() {
        assertThat(ObjectSearch.find(objects, new User("Kevin", "email"))).isEqualTo(9);
    }

    @Test
    public void ifUserNotInArrayRecursive() {
        assertThat(ObjectSearch.find(objects, new User("Kevi", "email"))).isEqualTo(-1);
    }

    @Test
    public void ifStringInArrayLinear() {
        Object[] simpleObjects = new Object[]{1, "Two", new User("Three", "none"), new EmailNotitfication()};
        assertThat(ObjectSearch.find(simpleObjects, "Two")).isEqualTo(1);
    }
}