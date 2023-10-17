package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != account;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        boolean flag = from.isPresent() && to.isPresent() && (from.get().amount() - amount) >= 0;
        if (flag) {
            update(new Account(from.get().id(), from.get().amount() - amount));
            update(new Account(to.get().id(), to.get().amount() + amount));
        }
        return flag;
    }
}