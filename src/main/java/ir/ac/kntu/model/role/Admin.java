package ir.ac.kntu.model.role;

import ir.ac.kntu.model.Account;

public class Admin {
    private final Account account;

    public Admin(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
