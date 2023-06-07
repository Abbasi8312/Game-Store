package ir.ac.kntu.model;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.utility.ErrorType;

import java.util.Objects;

public class Account extends AccountRoles{
    private final DB db;

    private String name;

    private String phone;

    private String email;

    private String password;

    public Account(DB db) {
        super();
        this.db = db;
    }

    public Account(DB db, String name, String password, String email, String phone) {
        super();
        this.db = db;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public boolean canLogin(String password) {
        return password.equals(this.password);
    }

    public String getName() {
        return name;
    }

    public ErrorType setName(String name) {
        if (db.accountsDB.findAccountByName(name) != null) {
            return ErrorType.INDISTINCT;
        } else if (name.matches("^[A-Za-z][A-Za-z0-9 _-]*$")) {
            db.accountsDB.changeName(this.name, name);
            this.name = name;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getPhone() {
        return phone;
    }

    public ErrorType setPhone(String phone) {
        if (db.accountsDB.findAccountByPhone(phone) != null) {
            return ErrorType.INDISTINCT;
        } else if (phone.matches("^\\+?\\d+$")) {
            db.accountsDB.changePhone(this.phone, phone);
            this.phone = phone;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getEmail() {
        return email;
    }

    public ErrorType setEmail(String email) {
        if (db.accountsDB.findAccountByEmail(email) != null) {
            return ErrorType.INDISTINCT;
        } else if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            db.accountsDB.changeEmail(this.email, email);
            this.email = email;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (password.matches(".*[a-z].*")) {
            if (password.matches(".*[A-Z].*")) {
                if (password.matches(".*[0-9].*")) {
                    if (password.length() >= 8) {
                        this.password = password;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(name, account.name) && Objects.equals(phone, account.phone) &&
                Objects.equals(email, account.email) && Objects.equals(password, account.password);
    }

    @Override public int hashCode() {
        return Objects.hash(name, phone, email, password);
    }

    @Override public String toString() {
        return "Account{" + "name='" + name + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' +
                ", password='" + password + '\'' + '}';
    }
}
