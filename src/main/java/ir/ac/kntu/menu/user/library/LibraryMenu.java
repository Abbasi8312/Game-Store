package ir.ac.kntu.menu.user.library;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.model.Accessory;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.Product;
import ir.ac.kntu.model.role.User;

public class LibraryMenu extends UserMenu {
    public LibraryMenu(DB db, User user) {
        super(db);
        currentUser = user;
    }

    public void gameOptions(Game game) {
        System.out.println(game);
        System.out.println("1. Community");
        System.out.println("2. Rate");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> productCommunity(game);
                case "2" -> gameRate(game);
                default -> System.out.println("Invalid input");
            }
            System.out.println(game);
            System.out.println("1. Community");
            System.out.println("2. Rate");
            getInput();
            clearScreen();
        }
    }

    public void accessoryOptions(Accessory accessory) {
        System.out.println(accessory);
        System.out.println("1. Community");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                productCommunity(accessory);
            } else {
                System.out.println("Invalid input");
            }
            System.out.println(accessory);
            System.out.println("1. Community");
            getInput();
            clearScreen();
        }
    }

    void productCommunity(Product product) {
        for (String key : product.getReviews().keySet()) {
            System.out.println(key + ":");
            System.out.println(product.getReview(key));
        }
        System.out.println("Enter your review of the product");
        getInput();
        clearScreen();
        if (canContinue()) {
            if (input.equals("")) {
                product.removeReview(currentUser.account.getName());
            } else {
                product.addReview(currentUser.account.getName(), input);
            }
        }
    }

    void gameRate(Game game) {
        System.out.println("Enter a number between 1 and 10 or just press enter to remove your rating");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("[0-9]+(\\.[0-9]+)?")) {
                if (Double.parseDouble(input) >= 1 && Double.parseDouble(input) <= 10) {
                    game.rate(currentUser.account.getName(), Double.parseDouble(input));
                    break;
                } else {
                    System.out.println("Your rate should be between 1 and 10");
                }
            } else if (input.equals("")) {
                game.removeRate(currentUser.account.getName());
                break;
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Enter a number between 1 and 10");
            getInput();
            clearScreen();
        }
    }
}
