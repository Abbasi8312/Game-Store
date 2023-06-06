package ir.ac.kntu.menu.admin.users;

import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.auth.AuthMenu;
import ir.ac.kntu.menu.user.profile.ProfileMenu;
import ir.ac.kntu.model.User2;
import ir.ac.kntu.utility.ConsoleCommand;

public class AdminUserMenu extends AdminMenu {

    private boolean back = false;

    public void userOptions() {
        System.out.println("1. Create new user");
        System.out.println("2. Modify an existing user");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new AuthMenu().userSignUpUsername(false);
                case "2" -> getUser();
                default -> System.out.println("Invalid input");
            }
            back = false;
            System.out.println("1. Create new user");
            System.out.println("2. Modify an existing user");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void getUser() {
        System.out.println("1. Find user by username");
        System.out.println("2. Find user by phone number");
        System.out.println("3. Find user by email address");
        getInput();
        ConsoleCommand.clearScreen();
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
            ConsoleCommand.clearScreen();
        }
    }

    public void findByUsername() {
        System.out.println("Enter username");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            User2 user = DB.findUserByUsername(input);
            if (user != null) {
                modifyUserOptions(user);
            } else {
                System.out.println("There is no user with this username");
            }
            if (!back) {
                System.out.println("Enter username");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    public void findByPhoneNumber() {
        System.out.println("Enter phone number");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            User2 user = DB.findUserByPhoneNumber(input);
            if (user != null) {
                modifyUserOptions(user);
            } else {
                System.out.println("There is no user with this phone number");
            }
            if (!back) {
                System.out.println("Enter phone number");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    public void findByEmail() {
        System.out.println("Enter email address");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            User2 user = DB.findUserByEmail(input);
            if (user != null) {
                modifyUserOptions(user);
            } else {
                System.out.println("There is no user with this email address");
            }
            if (!back) {
                System.out.println("Enter email address");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    public void modifyUserOptions(User2 user) {
        System.out.println("1. Show user profile");
        System.out.println("2. Change user profile");
        System.out.println("3. Change user wallet balance");
        System.out.println("4. Delete user");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> showUserProfile(user);
                case "2" -> new ProfileMenu(user).changeProfile();
                case "3" -> changeUserWallet(user);
                case "4" -> deleteUser(user);
                default -> System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("1. Show user profile");
                System.out.println("2. Change user profile");
                System.out.println("3. Change user wallet balance");
                System.out.println("4. Delete user");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
        back = true;
    }

    public void showUserProfile(User2 user) {
        System.out.println(user);
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            System.out.println(user);
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void changeUserWallet(User2 user) {
        System.out.println("Enter a new wallet balance for this account");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                user.setWalletBalance(Double.parseDouble(input));
                break;
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Enter a new wallet balance for this account");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void deleteUser(User2 user) {
        System.out.println("Are you sure you want to delete " + user.getUsername() + "?");
        System.out.println("1. Yes");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                DB.removeUser(user);
                back = true;
                break;
            } else {
                System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("Are you sure you want to delete " + user.getUsername() + "?");
                System.out.println("1. Yes");
                getInput();
                ConsoleCommand.clearScreen();
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
