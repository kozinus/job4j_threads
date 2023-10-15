package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            boolean flag = !accounts.containsKey(account.id());
            if (flag) {
                accounts.put(account.id(), account);
            }
            return flag;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            boolean flag = accounts.containsKey(account.id());
            if (flag) {
                accounts.put(account.id(), account);
            }
            return flag;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
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
}