package ir.ac.kntu.menu.user.product;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.games.AdminGameMenu;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.menu.user.library.LibraryMenu;
import ir.ac.kntu.menu.user.store.StoreMenu;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.Product;

import java.util.List;

public class SelectGame extends UserMenu {
    private final NextMenu nextMenu;

    private final List<Product> products;

    private final Account currentAccount;

    private boolean back = false;

    public SelectGame(DB db, NextMenu nextMenu, Account account, List<Product> games) {
        super(db);
        this.nextMenu = nextMenu;
        currentAccount = account;
        currentUser = account.user;
        this.products = games;
    }

    public void store() {
        System.out.println("1. Display all games");
        System.out.println("2. Filter by name");
        System.out.println("3. Filter by price");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> storeAllProducts();
                case "2" -> filterProductsByName();
                case "3" -> filterProductsByPrice();
                default -> System.out.println("Wrong input!");
            }
            if (!back) {
                System.out.println("1. Display all games");
                System.out.println("2. Filter by name");
                System.out.println("3. Filter by price");
                getInput();
                clearScreen();
            }
        }
    }

    void storeAllProducts() {
        while (canContinue()) {
            selectProduct(products, currentUser.account);
        }
    }

    void filterProductsByPrice() {
        System.out.println("Enter min and max value seperated by space");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?\\s+[0-9]+(\\.[0-9]+)?$")) {
                List<Product> products;
                if (nextMenu == NextMenu.STORE || nextMenu == NextMenu.ADMIN) {
                    products = db.productsDB.filterProductsByPrice(Double.parseDouble(input.split(" ")[0]),
                            Double.parseDouble(input.split(" ")[1]));
                } else {
                    products = currentUser.filterProductsByPrice(Double.parseDouble(input.split(" ")[0]),
                            Double.parseDouble(input.split(" ")[1]));
                }
                selectProduct(products, currentUser.account);
            } else {
                System.out.println("Invalid input!");
            }
            if (!back) {
                System.out.println("Enter min and max value seperated by space");
                getInput();
                clearScreen();
            }
        }
    }

    private void selectProduct(List<Product> products, Account account) {
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + 1 + ". " + products.get(i).getName());
        }
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+$") && Integer.parseInt(input) <= products.size() &&
                    Integer.parseInt(input) > 0) {
                Product product = products.get(Integer.parseInt(input) - 1);
                switch (nextMenu) {
                    case STORE -> new StoreMenu(db, currentUser).storeProduct(product);
                    case LIBRARY -> {
                        if (product instanceof Game) {
                            new LibraryMenu(db, currentUser).gameOptions((Game) product);
                        }/* else if (product instanceof Accessory) {

                        }*/
                    }
                    case ADMIN -> {
                        if (product instanceof Game) {
                            new AdminGameMenu(db, account.admin).modifyGameOptions((Game) product);
                        }/* else if (product instanceof Accessory) {

                        }*/
                        back = true;
                    }
                    default -> showProduct(product);
                }
            } else {
                System.out.println("Wrong input!");
            }
            if (!back) {
                for (int i = 0; i < products.size() && products.get(i) != null; i++) {
                    System.out.println(i + 1 + ". " + products.get(i).getName());
                }
                getInput();
                clearScreen();
            }
        }
    }

    void showProduct(Product product) {
        System.out.println(product);
        getInput();
        clearScreen();
        while (canContinue()) {
            System.out.println("Invalid Input");
            System.out.println(product);
            getInput();
            clearScreen();
        }
    }

    void filterProductsByName() {
        System.out.println("What do you want to search?");
        getInput();
        clearScreen();
        while (canContinue()) {
            List<Product> products;
            if (nextMenu == NextMenu.STORE || nextMenu == NextMenu.ADMIN) {
                products = db.productsDB.filterProductsByName(input);
            } else {
                products = currentUser.filterProductsByName(input);
            }
            selectProduct(products, currentUser.account);
            if (!back) {
                System.out.println("What do you want to search?");
                getInput();
                clearScreen();
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
