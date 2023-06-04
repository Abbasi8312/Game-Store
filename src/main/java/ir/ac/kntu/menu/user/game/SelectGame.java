package ir.ac.kntu.menu.user.game;

import ir.ac.kntu.model.Game;
import ir.ac.kntu.menu.admin.games.AdminGameMenu;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.menu.user.library.LibraryMenu;
import ir.ac.kntu.menu.user.store.StoreMenu;
import ir.ac.kntu.model.User;
import ir.ac.kntu.utility.ConsoleCommand;

import java.util.List;

public class SelectGame extends UserMenu {
    private final NextMenu nextMenu;

    private final List<Game> games;

    private boolean back = false;

    public SelectGame(NextMenu nextMenu, User user, List<Game> games) {
        super();
        this.nextMenu = nextMenu;
        currentUser = user;
        this.games = games;
    }

    public void store() {
        System.out.println("1. Display all games");
        System.out.println("2. Filter by name");
        System.out.println("3. Filter by price");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> storeAllGames();
                case "2" -> filterGamesByName();
                case "3" -> filterGamesByPrice();
                default -> System.out.println("Wrong input!");
            }
            if (!back) {
                System.out.println("1. Display all games");
                System.out.println("2. Filter by name");
                System.out.println("3. Filter by price");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    void storeAllGames() {
        while (canContinue()) {
            selectGame(games);
        }
    }

    void filterGamesByPrice() {
        System.out.println("Enter min and max value seperated by space");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?\\s+[0-9]+(\\.[0-9]+)?$")) {
                List<Game> games;
                if (nextMenu == NextMenu.STORE || nextMenu == NextMenu.ADMIN) {
                    games = database.filterGameByPrice(Double.parseDouble(input.split(" ")[0]),
                            Double.parseDouble(input.split(" ")[1]));
                } else {
                    games = currentUser.filterGameByPrice(Double.parseDouble(input.split(" ")[0]),
                            Double.parseDouble(input.split(" ")[1]));
                }
                selectGame(games);
            } else {
                System.out.println("Invalid input!");
            }
            if (!back) {
                System.out.println("Enter min and max value seperated by space");
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    private void selectGame(List<Game> games) {
        for (int i = 0; i < games.size(); i++) {
            System.out.println(i + 1 + ". " + games.get(i).getName());
        }
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+$") && Integer.parseInt(input) <= games.size() && Integer.parseInt(input) > 0) {
                Game game = games.get(Integer.parseInt(input) - 1);
                switch (nextMenu) {
                    case STORE -> new StoreMenu(currentUser).storeGame(game);
                    case LIBRARY -> new LibraryMenu(currentUser).gameOptions(game);
                    case ADMIN -> {
                        new AdminGameMenu().modifyGameOptions(game);
                        back = true;
                    }
                    default -> showGame(game);
                }
            } else {
                System.out.println("Wrong input!");
            }
            if (!back) {
                for (int i = 0; i < games.size() && games.get(i) != null; i++) {
                    System.out.println(i + 1 + ". " + games.get(i).getName());
                }
                getInput();
                ConsoleCommand.clearScreen();
            }
        }
    }

    void showGame(Game game) {
        System.out.println(game);
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            System.out.println("Invalid Input");
            System.out.println(game);
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    void filterGamesByName() {
        System.out.println("What do you want to search?");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            List<Game> games;
            if (nextMenu == NextMenu.STORE || nextMenu == NextMenu.ADMIN) {
                games = database.filterGameByName(input);
            } else {
                games = currentUser.filterGameByName(input);
            }
            selectGame(games);
            if (!back) {
                System.out.println("What do you want to search?");
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
