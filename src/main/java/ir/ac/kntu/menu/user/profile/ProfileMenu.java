package ir.ac.kntu.menu.user.profile;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.model.role.User;

public class ProfileMenu extends UserMenu {
    private boolean changedProfile;

    public ProfileMenu(DB db, User user) {
        super(db);
        currentUser = user;
    }

    public void profile() {
        System.out.println(currentUser);
        System.out.println("Enter a number.");
        System.out.println("1. Change profile");
        System.out.println("2. Charge wallet");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> changeProfile();
                case "2" -> chargeWallet();
                default -> System.out.println("Wrong input!");
            }
            System.out.println(currentUser);
            System.out.println("1. Change profile");
            System.out.println("2. Charge wallet");
            getInput();
            clearScreen();
            changedProfile = false;
        }
    }

    public void changeProfile() {
        System.out.println("1. Change username");
        System.out.println("2. Change password");
        System.out.println("3. Change email address");
        System.out.println("4. Change phone number");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> changeUsername();
                case "2" -> changePassword();
                case "3" -> changeEmail();
                case "4" -> changePhoneNumber();
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

    public void changeUsername() {
        System.out.println("Enter a new username for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (currentUser.account.setName(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (currentUser.account.getName().equals(input)) {
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

    public void changePassword() {
        System.out.println("Enter a new password for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (currentUser.account.setPassword(input)) {
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

    public void changeEmail() {
        System.out.println("Enter a new email address for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (currentUser.account.setEmail(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (currentUser.account.getEmail().equals(input)) {
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

    public void changePhoneNumber() {
        System.out.println("Enter a new phone number for this account:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (currentUser.account.setPhone(input)) {
                case NONE -> {
                    changedProfile = true;
                    return;
                }
                case INDISTINCT -> {
                    if (currentUser.account.getPhone().equals(input)) {
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

    public void chargeWallet() {
        System.out.println("How much do you want to charge your wallet?");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]{0,5}\\.?[0-9]{0,5}$") && !input.equals(".") && input.matches("^.*[0-9].*$")) {
                currentUser.chargeWallet(Double.parseDouble(input));
                break;
            } else {
                System.out.println("Invalid input!");
            }
            System.out.println("How much do you want to charge your wallet?");
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
