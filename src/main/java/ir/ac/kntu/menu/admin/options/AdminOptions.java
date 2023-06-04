package ir.ac.kntu.menu.admin.options;

import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.admin.games.AdminGameMenu;
import ir.ac.kntu.menu.admin.users.AdminUserMenu;
import ir.ac.kntu.utility.ConsoleCommand;

public class AdminOptions extends AdminMenu {
    public void adminOptions() {
        System.out.println("1. Games");
        System.out.println("2. Users");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new AdminGameMenu().gameOptions();
                case "2" -> new AdminUserMenu().userOptions();
                default -> System.out.println("Invalid input");
            }
            System.out.println("1. Games");
            System.out.println("2. Users");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }
}
