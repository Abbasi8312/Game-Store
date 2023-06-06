package ir.ac.kntu.menu.auth;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.admin.options.AdminOptions;
import ir.ac.kntu.menu.user.options.UserOptions;
import ir.ac.kntu.model.Admin;
import ir.ac.kntu.model.User2;

import static ir.ac.kntu.utility.ConsoleCommand.clearScreen;

public class AuthMenu extends Menu {
    private boolean loggedOut;

    public AuthMenu() {
        loggedOut = false;
    }

    public void mainOptions() {
        clearScreen();
        System.out.println("Welcome to Fariborz Game Store!");
        System.out.println("You can use \"*\" anywhere to go back and \"**\" to exit the program.");
        System.out.println("1. User");
        System.out.println("2. Admin");
        getInput();
        clearScreen();
        while (!input.equals("**")) {
            switch (input) {
                case "1" -> userOptions();
                case "2" -> adminLoginUsername();
                case "*" -> {
                }
                default -> System.out.println("Invalid input");
            }
            System.out.println("Welcome to Fariborz Game Store!");
            System.out.println("You can use \"*\" anywhere to go back and \"**\" to exit the program.");
            System.out.println("1. User");
            System.out.println("2. Admin");
            getInput();
            clearScreen();
            loggedOut = false;
        }
    }

    public void userOptions() {
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> userLoginUsername();
                case "2" -> userSignUpUsername(true);
                default -> System.out.println("Invalid input");
            }
            if (!loggedOut) {
                System.out.println("1. Login");
                System.out.println("2. Sign Up");
                getInput();
                clearScreen();
            }
        }
    }

    public void adminLoginUsername() {
        System.out.println("Enter admin username:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals(DB.getAdmin().getUserName())) {
                adminLoginPassword(DB.getAdmin());
            } else {
                adminLoginPassword(null);
            }
            if (!loggedOut) {
                System.out.println("Enter admin username:");
                getInput();
                clearScreen();
            }
        }
    }

    public void adminLoginPassword(Admin admin) {
        System.out.println("Enter admin password:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (admin == null || !input.equals(admin.getPassword())) {
                System.out.println("Username or password is incorrect");
            } else {
                new AdminOptions().adminOptions();
            }
            loggedOut = true;
        }
    }

    public void userLoginUsername() {
        System.out.println("Enter username:");
        getInput();
        clearScreen();
        while (canContinue()) {
            User2 user = DB.findUserByUsername(input);
            userLoginPassword(user);
            if (!loggedOut) {
                System.out.println("Enter username:");
                getInput();
                clearScreen();
            }
        }
    }

    public void userLoginPassword(User2 user) {
        System.out.println("Enter password:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (user == null || !user.canLogin(input)) {
                System.out.println("Username or password is incorrect");
            } else {
                new UserOptions(user).userOptions();
            }
            loggedOut = true;
        }
    }

    public void userSignUpUsername(boolean login) {
        User2 user = new User2();
        System.out.println("Sign up:");
        System.out.println("Enter username:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (user.setUsername(input)) {
                case NONE -> userSignUpPassword(user, login);
                case INDISTINCT -> System.out.println("This username is already in use");
                default -> System.out.println(
                        "Username must start with an english letter and can contain letters, numbers, space, '-' and " +
                                "'_'");
            }
            if (!loggedOut) {
                System.out.println("Enter username:");
                getInput();
                clearScreen();
            }
        }
    }

    public void userSignUpPassword(User2 user, boolean login) {
        System.out.println("Enter password:");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (user.setPassword(input)) {
                userSignUpEmail(user, login);
            } else {
                System.out.println("Password must contain at least one lowercase letter, one uppercase letter, one " +
                        "digit, and be at least 8 characters long.");
            }
            if (!loggedOut) {
                System.out.println("Enter password:");
                getInput();
                clearScreen();
            }
        }
    }

    public void userSignUpEmail(User2 user, boolean login) {
        System.out.println("Enter email address:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (user.setEmail(input)) {
                case NONE -> userSignUpPhoneNumber(user, login);
                case INDISTINCT -> System.out.println("This email address is already in use");
                default -> System.out.println("Invalid email address!");
            }
            if (!loggedOut) {
                System.out.println("Enter email address:");
                getInput();
                clearScreen();
            }
        }
    }

    public void userSignUpPhoneNumber(User2 user, boolean login) {
        System.out.println("Enter phone number:");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (user.setPhoneNumber(input)) {
                case NONE -> {
                    DB.addUser(user);
                    if (login) {
                        new UserOptions(user).userOptions();
                    }
                    loggedOut = true;
                }
                case INDISTINCT -> System.out.println("This phone address is already in use");
                default -> System.out.println("Invalid phone number!");
            }
            if (!loggedOut) {
                System.out.println("Enter phone number:");
                getInput();
                clearScreen();
            }
        }
    }

    @Override protected void getInput() {
        input = scannerWrapper.nextLine();
    }

    @Override protected boolean canContinue() {
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
