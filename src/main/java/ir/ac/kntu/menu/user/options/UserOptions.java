package ir.ac.kntu.menu.user.options;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.menu.user.friends.FriendsMenu;
import ir.ac.kntu.menu.user.product.NextMenu;
import ir.ac.kntu.menu.user.product.SelectGame;
import ir.ac.kntu.menu.user.profile.ProfileMenu;
import ir.ac.kntu.model.role.User;

public class UserOptions extends UserMenu {
    public UserOptions(DB db, User user) {
        super(db);
        currentUser = user;
    }

    public void userOptions() {
        System.out.println("Enter a number.");
        System.out.println("1. Profile");
        System.out.println("2. Store");
        System.out.println("3. Library");
        System.out.println("4. Friends");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new ProfileMenu(db, currentUser).profile();
                case "2" ->
                        new SelectGame(db, NextMenu.STORE, currentUser.account, db.productsDB.getAllProducts()).store();
                case "3" ->
                        new SelectGame(db, NextMenu.LIBRARY, currentUser.account, currentUser.getProducts()).store();
                case "4" -> new FriendsMenu(db, currentUser).friendsOptions();
                default -> System.out.println("Wrong input!");
            }
            System.out.println("Enter a number.");
            System.out.println("1. Profile");
            System.out.println("2. Store");
            System.out.println("3. Library");
            System.out.println("4. Friends");
            getInput();
            clearScreen();
        }
    }
}
