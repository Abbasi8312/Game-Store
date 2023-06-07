package ir.ac.kntu.menu.admin.options;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.admin.products.AdminProductMenu;
import ir.ac.kntu.menu.admin.profile.ProfileMenu;
import ir.ac.kntu.menu.admin.users.AdminUserMenu;
import ir.ac.kntu.model.role.Admin;

public class AdminOptions extends AdminMenu {
    public AdminOptions(DB db, Admin admin) {
        super(db, admin);
    }

    public void adminOptions() {
        System.out.println("1. Profile");
        System.out.println("2. Products");
        System.out.println("3. Users");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new ProfileMenu(db).changeProfile(currentAdmin.account);
                case "2" -> new AdminProductMenu(db, currentAdmin).gameOptions();
                case "3" -> new AdminUserMenu(db, currentAdmin).userOptions();
                default -> System.out.println("Invalid input");
            }
            System.out.println("1. Profile");
            System.out.println("2. Products");
            System.out.println("3. Users");
            getInput();
            clearScreen();
        }
    }
}
