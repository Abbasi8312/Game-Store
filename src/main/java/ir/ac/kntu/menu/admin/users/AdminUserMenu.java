package ir.ac.kntu.menu.admin.users;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.auth.AuthMenu;
import ir.ac.kntu.menu.user.profile.ProfileMenu;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.role.Admin;
import ir.ac.kntu.model.role.User;

public class AdminUserMenu extends AdminMenu {

    private boolean back = false;

    public AdminUserMenu(DB db, Admin admin) {
        super(db, admin);
    }

    public void userOptions() {
        System.out.println("1. Create new user");
        System.out.println("2. Modify an existing user");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new AuthMenu(db).signUpUsername(false);
                case "2" -> getUser();
                default -> System.out.println("Invalid input");
            }
            back = false;
            System.out.println("1. Create new user");
            System.out.println("2. Modify an existing user");
            getInput();
            clearScreen();
        }
    }

    public void getUser() {
        System.out.println("1. Find user by username");
        System.out.println("2. Find user by phone number");
        System.out.println("3. Find user by email address");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> findByUsername();
                case "2" -> findByPhoneNumber();
                case "3" -> findByEmail();
                default -> System.out.println("Invalid input");
            }
            back = false;
            System.out.println("1. Find user by username");
            System.out.println("2. Find user by phone number");
            System.out.println("3. Find user by email address");
            getInput();
            clearScreen();
        }
    }

    public void findByUsername() {
        System.out.println("Enter username");
        getInput();
        clearScreen();
        while (canContinue()) {
            Account account = db.accountsDB.findAccountByName(input);
            if (account != null) {
                modifyUserOptions(account);
            } else {
                System.out.println("There is no account with this username");
            }
            if (!back) {
                System.out.println("Enter username");
                getInput();
                clearScreen();
            }
        }
    }

    public void findByPhoneNumber() {
        System.out.println("Enter phone number");
        getInput();
        clearScreen();
        while (canContinue()) {
            Account account = db.accountsDB.findAccountByPhone(input);
            if (account != null) {
                modifyUserOptions(account);
            } else {
                System.out.println("There is no account with this phone number");
            }
            if (!back) {
                System.out.println("Enter phone number");
                getInput();
                clearScreen();
            }
        }
    }

    public void findByEmail() {
        System.out.println("Enter email address");
        getInput();
        clearScreen();
        while (canContinue()) {
            Account account = db.accountsDB.findAccountByEmail(input);
            if (account != null) {
                modifyUserOptions(account);
            } else {
                System.out.println("There is no account with this email address");
            }
            if (!back) {
                System.out.println("Enter email address");
                getInput();
                clearScreen();
            }
        }
    }

    public void modifyUserOptions(Account account) {
        System.out.println("1. Show account profile");
        System.out.println("2. Change account profile");
        System.out.println("3. Change account wallet balance");
        System.out.println("4. Delete account");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> showUserProfile(account);
                case "2" -> new ProfileMenu(db, account.user).changeProfile();
                case "3" -> changeUserWallet(account.user);
                case "4" -> deleteUser(account);
                default -> System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("1. Show account profile");
                System.out.println("2. Change account profile");
                System.out.println("3. Change account wallet balance");
                System.out.println("4. Delete account");
                getInput();
                clearScreen();
            }
        }
        back = true;
    }

    public void showUserProfile(Account account) {
        System.out.println(account);
        getInput();
        clearScreen();
        while (canContinue()) {
            System.out.println(account);
            getInput();
            clearScreen();
        }
    }

    public void changeUserWallet(User user) {
        System.out.println("Enter a new wallet balance for this account");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                user.setWalletBalance(Double.parseDouble(input));
                break;
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Enter a new wallet balance for this account");
            getInput();
            clearScreen();
        }
    }

    public void deleteUser(Account account) {
        System.out.println("Are you sure you want to delete " + account.getName() + "?");
        System.out.println("1. Yes");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                db.accountsDB.removeAccount(account);
                back = true;
                break;
            } else {
                System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("Are you sure you want to delete " + account.getName() + "?");
                System.out.println("1. Yes");
                getInput();
                clearScreen();
            }
        }
    }

    @Override protected boolean canContinue() {
        if (input.equals("**")) {
            System.out.println("Exiting...");
            System.exit(0);
            return false;
        } else if (back) {
            return false;
        } else {
            return !input.equals("*");
        }
    }
}
