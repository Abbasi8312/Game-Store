package ir.ac.kntu.menu.user.store;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.model.Accessory;
import ir.ac.kntu.model.Product;
import ir.ac.kntu.model.role.User;
import ir.ac.kntu.utility.ErrorType;

public class StoreMenu extends UserMenu {
    public StoreMenu(DB db, User user) {
        super(db);
        currentUser = user;
    }

    public void storeProduct(Product product) {
        System.out.println(product);
        boolean canAddGame = !currentUser.hasProduct(product);
        if (canAddGame || product instanceof Accessory) {
            System.out.println("1. Buy product");
        }
        System.out.println("2. Gift product");
        getInput();
        clearScreen();
        while (canContinue()) {
            if ((canAddGame || product instanceof Accessory) && input.equals("1")) {
                ErrorType errorType = currentUser.buyProduct(product);
                if (errorType == ErrorType.NONE) {
                    System.out.println(product.getName() + " is added to your library");
                } else if (errorType == ErrorType.NOT_ENOUGH_BALANCE) {
                    System.out.println("You don't have enough balance in your wallet to buy this product");
                } else if (errorType == ErrorType.LOW_POINTS) {
                    System.out.println("You don't have enough points to buy this game");
                }
                break;
            } else if (input.equals("2")) {
                giftGame(product);
                break;
            } else {
                System.out.println("Invalid input!");
            }
            System.out.println(product);
            if (canAddGame || product instanceof Accessory) {
                System.out.println("1. Buy product");
            }
            System.out.println("2. Gift product");
            getInput();
            clearScreen();
        }
    }

    public void giftGame(Product product) {
        System.out.println("Enter a username");
        getInput();
        clearScreen();
        while (canContinue()) {
            User user = db.accountsDB.findAccountByName(input).user;
            if (user != null) {
                ErrorType errorType = currentUser.buyProduct(product, user);
                if (errorType == ErrorType.NONE) {
                    System.out.println(product.getName() + " is added to user's library");
                } else if (errorType == ErrorType.NOT_ENOUGH_BALANCE) {
                    System.out.println("You don't have enough balance in your wallet to buy this product");
                } else {
                    System.out.println("User already has this product");
                }
                break;
            } else {
                System.out.println("There is no user with this username");
            }
            System.out.println("Enter a username");
            getInput();
            clearScreen();
        }
    }
}
