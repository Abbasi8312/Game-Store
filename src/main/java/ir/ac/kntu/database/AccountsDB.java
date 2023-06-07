package ir.ac.kntu.database;

import ir.ac.kntu.model.Account;
import ir.ac.kntu.utility.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AccountsDB {
    private final DB db;

    private final List<Account> accountList;

    private final HashMap<String, Account> accountsByName;

    private final HashMap<String, Account> accountsByPhone;

    private final HashMap<String, Account> accountsByEmail;

    private final Trie accountTrie;

    public AccountsDB(DB db) {
        this.db = db;
        accountList = new ArrayList<>();
        accountsByName = new HashMap<>();
        accountsByPhone = new HashMap<>();
        accountsByEmail = new HashMap<>();
        accountTrie = new Trie();
    }

    public void addAccount(Account account) {
        if (account.getName() != null) {
            accountsByName.put(account.getName(), account);
            accountsByPhone.put(account.getPhone(), account);
            accountsByEmail.put(account.getEmail(), account);
            accountList.add(account);
            accountTrie.insert(account.getName(), accountList.size() - 1);
        }
    }

    public void removeAccount(Account account) {
        for (Account account1 : accountList) {
            account1.user.removePendingRequest(account.user);
            account1.user.removeFriend(account.user);
        }
        accountsByName.remove(account.getName());
        accountsByPhone.remove(account.getPhone());
        accountsByEmail.remove(account.getEmail());
        accountTrie.remove(account.getName(), accountList.indexOf(account));
        accountList.remove(account);
    }

    public Account login(String name, String password) {
        Account account = findAccountByName(name);
        if (account != null && account.canLogin(password)) {
            return account;
        }
        return null;
    }


    public Account findAccountByName(String name) {
        return accountsByName.get(name);
    }

    public Account findAccountByPhone(String phone) {
        return accountsByPhone.get(phone);
    }

    public Account findAccountByEmail(String email) {
        return accountsByEmail.get(email);
    }

    public void changeName(String oldAccountname, String newAccountname) {
        accountsByName.put(newAccountname, accountsByName.get(oldAccountname));
        accountsByName.remove(oldAccountname);
    }

    public void changePhone(String oldPhoneNumber, String newPhoneNumber) {
        accountsByPhone.put(newPhoneNumber, accountsByPhone.get(oldPhoneNumber));
        accountsByPhone.remove(oldPhoneNumber);
    }

    public void changeEmail(String oldEmail, String newEmail) {
        accountsByEmail.put(newEmail, accountsByEmail.get(oldEmail));
        accountsByEmail.remove(oldEmail);
    }

    public List<Account> getAccountList() {
        return new ArrayList<>(accountList);
    }

    public HashMap<String, Account> getAccountsByName() {
        return new HashMap<>(accountsByName);
    }

    public HashMap<String, Account> getAccountsByPhone() {
        return new HashMap<>(accountsByPhone);
    }

    public HashMap<String, Account> getAccountsByEmail() {
        return new HashMap<>(accountsByEmail);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountsDB accountsDB = (AccountsDB) o;
        return Objects.equals(accountList, accountsDB.accountList) &&
                Objects.equals(accountsByName, accountsDB.accountsByName) &&
                Objects.equals(accountsByPhone, accountsDB.accountsByPhone) &&
                Objects.equals(accountsByEmail, accountsDB.accountsByEmail) &&
                Objects.equals(accountTrie, accountsDB.accountTrie);
    }

    @Override public int hashCode() {
        return Objects.hash(accountList, accountsByName, accountsByPhone, accountsByEmail, accountTrie);
    }

    @Override public String toString() {
        return "AccountDB{" + "accountList=" + accountList + '}';
    }
}
