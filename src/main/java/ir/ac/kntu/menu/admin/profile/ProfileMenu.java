package ir.ac.kntu.menu.admin.profile;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.model.Account;

public class ProfileMenu extends Menu {
    private boolean changedProfile;

    public ProfileMenu(DB db) {
        super(db);
    }

    public void changeProfile(Account account) {
        System.out.println("1. Change username");
        System.out.println("2. Change password");
        System.out.println("3. Change email address");
        System.out.println("4. Change phone number");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> changeUsername(account);
                case "2" -> changePassword(account);
                case "3" -> changeEmail(account);
                case "4" -> changePhoneNumber(account);
                default -> System.out.println("Wrong input!");
            }
            if (!changedProfile) {
                System.out.println("1. Change username");
                System.out.println("2. Change password");
                System.out.println("3. Change email address");
                System.out.println("4. Change phone number");
                getInput();
                clearScreen();
            }
        }
    }

    public void changeUsername(Account account) {
        System.out.println("Enter a new username for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (account.setName(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (account.getName().equals(input)) {
                        System.out.println("No changes have been made to the username.");
                        changedProfile = true;
                        return;
                    } else {
                        System.out.println("This username is already in use!");
                    }
                }
                default -> System.out.println(
                        "Username must start with an english letter and can contain letters, numbers, space, '-' and " +
                                "'_'");
            }
            System.out.println("Enter a new username for this account:");
            getInput();
            clearScreen();
        }
    }

    public void changePassword(Account account) {
        System.out.println("Enter a new password for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (account.setPassword(input)) {
                changedProfile = true;
                break;
            } else {
                System.out.println("Password must contain at least one lowercase letter, one uppercase letter, one " +
                        "digit, and be at least 8 characters long.");
            }
            System.out.println("Enter a new password for this account:");
            getInput();
            clearScreen();
        }
    }

    public void changeEmail(Account account) {
        System.out.println("Enter a new email address for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (account.setEmail(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (account.getEmail().equals(input)) {
                        System.out.println("No changes have been made to the email address.");
                        changedProfile = true;
                        return;
                    } else {
                        System.out.println("This email address is already in use!");
                    }
                }
                default -> System.out.println("Invalid email address!");
            }
            System.out.println("Enter a new email address for this account:");
            getInput();
            clearScreen();
        }
    }

    public void changePhoneNumber(Account account) {
        System.out.println("Enter a new phone number for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (account.setPhone(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (account.getPhone().equals(input)) {
                        System.out.println("No changes have been made to the phone number.");
                        changedProfile = true;
                        return;
                    } else {
                        System.out.println("This phone number is already in use!");
                    }
                }
                default -> System.out.println("Invalid phone number!");
            }
            System.out.println("Enter a new phone number for this account:");
            getInput();
            clearScreen();
        }
    }

    @Override protected boolean canContinue() {
        if (input.equals("**")) {
            System.out.println("Exiting...");
            System.exit(0);
            return false;
        } else if (changedProfile) {
            return false;
        } else {
            return !input.equals("*");
        }
    }
}
