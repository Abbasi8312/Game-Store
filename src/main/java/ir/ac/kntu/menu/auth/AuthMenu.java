package ir.ac.kntu.menu.auth;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.admin.options.AdminOptions;
import ir.ac.kntu.menu.user.options.UserOptions;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.role.Role;
import ir.ac.kntu.utility.ScannerWrapper;

public class AuthMenu extends Menu {
    private boolean loggedOut;

    public AuthMenu(DB db) {
        super(db);
        loggedOut = false;
    }

    public void show() {
        AuthenticationOption option;
        while ((option = printMenuOptions("Authentication Menu", AuthenticationOption.class)) != null) {
            switch (option) {
                case LOGIN -> accountLogin();
                case REGISTER -> signUpUsername(true);
                default -> {
                }
            }
        }
    }

    private void accountLogin() {
        clearScreen();
        System.out.print("Enter username:");
        String username = ScannerWrapper.nextLine();
        System.out.print("Enter password:");
        String password = ScannerWrapper.nextLine();
        if (db.accountsDB.login(username, password) == null) {
            System.out.println("Wrong username or password");
        } else {
            selectRole(db.accountsDB.login(username, password));
        }
    }

    private void selectRole(Account account) {
        Role option;
        while ((option = printMenuOptions("Role", Role.class)) != null) {
            if (!account.hasRole(option)) {
                System.out.println("You don't have access to this menu");
                continue;
            }
            roleLogin(account, option);
        }
    }

    private void roleLogin(Account account, Role option) {
        switch (option) {
            case USER -> {
                new UserOptions(db, account.user).userOptions();
            }
            case GAME_DEVELOPER -> {
            }
            case ACCESSORY_SELLER -> {
            }
            case ADMIN -> {
                new AdminOptions(db, account.admin).adminOptions();
            }
            default -> {
            }
        }
    }

    public void signUpUsername(boolean login) {
        clearScreen();
        Account account = new Account(db);
        System.out.print("Enter username:");
        String username = ScannerWrapper.nextLine();
        while (canContinue(username)) {
            switch (account.setName(username)) {
                case NONE -> signUpPassword(account, login);
                case INDISTINCT -> {
                    clearScreen();
                    System.out.println("This username is already in use");
                }
                default -> {
                    clearScreen();
                    System.out.println(
                            "Username must start with an english letter and can contain letters, numbers, space, '-' " +
                                    "and " + "'_'");
                }
            }
            if (!loggedOut) {
                System.out.print("Enter username:");
                username = ScannerWrapper.nextLine();
                clearScreen();
            }
        }
    }

    public void signUpPassword(Account account, boolean login) {
        clearScreen();
        System.out.print("Enter password:");
        String password = ScannerWrapper.nextLine();
        while (canContinue(password)) {
            if (account.setPassword(password)) {
                signUpEmail(account, login);
            } else {
                clearScreen();
                System.out.println("Password must contain at least one lowercase letter, one uppercase letter, one " +
                        "digit, and be at least 8 characters long.");
            }
            if (!loggedOut) {
                System.out.print("Enter password:");
                password = ScannerWrapper.nextLine();
                clearScreen();
            }
        }
    }

    public void signUpEmail(Account account, boolean login) {
        clearScreen();
        System.out.print("Enter email address:");
        String email = ScannerWrapper.nextLine();
        while (canContinue(email)) {
            switch (account.setEmail(email)) {
                case NONE -> signUpPhoneNumber(account, login);
                case INDISTINCT -> {
                    clearScreen();
                    System.out.println("This email address is already in use");
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid email address!");
                }
            }
            if (!loggedOut) {
                System.out.print("Enter email address:");
                email = ScannerWrapper.nextLine();
                clearScreen();
            }
        }
    }

    public void signUpPhoneNumber(Account account, boolean login) {
        clearScreen();
        System.out.print("Enter phone number:");
        String phone = ScannerWrapper.nextLine();
        while (canContinue(phone)) {
            switch (account.setPhone(phone)) {
                case NONE -> {
                    db.accountsDB.addAccount(account);
                    signupRole(account, login);
                    loggedOut = true;
                }
                case INDISTINCT -> {
                    clearScreen();
                    System.out.println("This phone address is already in use");
                }
                default -> {
                    clearScreen();
                    System.out.println("Invalid phone number!");
                }
            }
            if (!loggedOut) {
                System.out.print("Enter phone number:");
                phone = ScannerWrapper.nextLine();
                clearScreen();
            }
        }
    }

    private void signupRole(Account account, boolean login) {
        Role option;
        while ((option = printMenuOptions("Role", Role.class)) != null) {
            switch (option) {
                case USER -> {
                    account.addRole(Role.USER);
                }
                case GAME_DEVELOPER -> {
                    account.addRole(Role.GAME_DEVELOPER);
                }
                case ACCESSORY_SELLER -> {
                    account.addRole(Role.ACCESSORY_SELLER);
                }
                case ADMIN -> {
                    account.addRole(Role.ADMIN);
                }
                default -> {
                }
            }
            if (login) {
                roleLogin(account, option);
            }
        }
    }

    protected boolean canContinue(String input) {
        if (input.equals("**")) {
            System.out.println("Exiting...");
            System.exit(0);
            return false;
        } else if (loggedOut) {
            return false;
        } else {
            return !input.equals("*");
        }
    }
}
