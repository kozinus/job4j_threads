package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> function = (key, keyValue) -> {
            if (keyValue.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base out = new Base(model.getId(), model.getVersion() + 1);
            out.setName(model.getName());
            return out;
        };
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}