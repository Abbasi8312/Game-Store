package ir.ac.kntu.menu.admin.games;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.user.product.NextMenu;
import ir.ac.kntu.menu.user.product.SelectGame;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.GameGenre;
import ir.ac.kntu.model.role.Admin;

import java.util.Arrays;

public class AdminGameMenu extends AdminMenu {
    private boolean back = false;

    public AdminGameMenu(DB db, Admin admin) {
        super(db, admin);
    }

    public void gameOptions() {
        System.out.println("1. Create new game");
        System.out.println("2. Modify an existing game");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> createGame();
                case "2" -> new SelectGame(db, NextMenu.ADMIN, currentAdmin.account,
                        db.productsDB.getAllProducts()).store();
                default -> System.out.println("Invalid input");
            }
            back = false;
            System.out.println("1. Create new game");
            System.out.println("2. Modify an existing game");
            getInput();
            clearScreen();
        }
    }

    public void createGame() {
        newGameName(new Game());
    }

    public void newGameName(Game game) {
        System.out.println("Enter a name for the game");
        getInput();
        clearScreen();
        while (canContinue()) {
            game.setName(input);
            newGamePrice(game);
            if (!back) {
                System.out.println("Enter a name for the game");
                getInput();
                clearScreen();
            }
        }
    }

    public void newGamePrice(Game game) {
        System.out.println("Enter a price for the game");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                game.setPrice(Double.parseDouble(input));
                newGameDescription(game);
            } else {
                System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("Enter a price for the game");
                getInput();
                clearScreen();
            }
        }
    }

    public void newGameDescription(Game game) {
        System.out.println("Enter a description for the game and then type \"end\" in a new line");
        StringBuilder stringBuilder = new StringBuilder();
        getInput();
        while (canContinue()) {
            if (!input.equals("end")) {
                stringBuilder.append(input).append("\n");
                getInput();
            } else {
                clearScreen();
                game.setDescription(String.valueOf(stringBuilder));
                newGameGenre(game);
            }
        }
    }

    public void newGameGenre(Game game) {
        System.out.println("Enter a Genre for the game");
        System.out.println("All Genres: " + Arrays.toString(GameGenre.values()));
        getInput();
        clearScreen();
        while (canContinue()) {
            GameGenre gameGenre = GameGenre.find(input);
            if (gameGenre != null) {
                game.setGenre(gameGenre);
                db.productsDB.addProduct(game);
                System.out.println("Game created successfully");
                back = true;
            } else {
                System.out.println("Invalid genre");
            }
            if (!back) {
                System.out.println("Enter a Genre for the game");
                System.out.println("All Genres: " + Arrays.toString(GameGenre.values()));
                getInput();
                clearScreen();
            }
        }
    }

    public void modifyGameOptions(Game game) {
        System.out.println("1. Change name");
        System.out.println("2. Change price");
        System.out.println("3. Change description");
        System.out.println("4. Change genre");
        System.out.println("5. Delete game");
        getInput();
        clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> changeGameName(game);
                case "2" -> changeGamePrice(game);
                case "3" -> changeGameDescription(game);
                case "4" -> changeGameGenre(game);
                case "5" -> deleteGame(game);
                default -> {
                    System.out.println("Invalid input");
                    System.out.println("1. Change name");
                    System.out.println("2. Change price");
                    System.out.println("3. Change description");
                    System.out.println("4. Change genre");
                    System.out.println("5. Delete game");
                    getInput();
                    clearScreen();
                    back = false;
                    continue;
                }
            }
            back = true;
        }
    }

    public void changeGameName(Game game) {
        System.out.println("Enter a new name for the game (Original name: " + game.getName() + ")");
        getInput();
        clearScreen();
        if (canContinue()) {
            game.setName(input);
            System.out.println("Name changed successfully");
        }
    }

    public void changeGamePrice(Game game) {
        System.out.println("Enter a new price for the game (Original price: " + game.getPrice() + ")");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                game.setPrice(Double.parseDouble(input));
                System.out.println("Price changed successfully");
                break;
            } else {
                System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("Enter a new price for the game (Original price: " + game.getPrice() + ")");
                getInput();
                clearScreen();
            }
        }
    }

    public void changeGameDescription(Game game) {
        System.out.println(
                "Enter a new description for the game and then type \"end\" in a new line (Original description: " +
                        game.getDescription() + ")");
        getInput();
        StringBuilder stringBuilder = new StringBuilder();
        while (canContinue()) {
            if (!input.equals("end")) {
                stringBuilder.append(input).append("\n");
                getInput();
            } else {
                clearScreen();
                game.setDescription(String.valueOf(stringBuilder));
                System.out.println("Description changed successfully");
                break;
            }
        }
    }

    public void changeGameGenre(Game game) {
        System.out.println("Enter a new Genre for the game (Original genre: " + game.getGenre() + ")");
        System.out.println("All Genres: " + Arrays.toString(GameGenre.values()));
        getInput();
        clearScreen();
        while (canContinue()) {
            GameGenre gameGenre = GameGenre.find(input);
            if (gameGenre != null) {
                game.setGenre(gameGenre);
                System.out.println("Genre changed successfully");
                break;
            } else {
                System.out.println("Invalid genre");
            }
            if (!back) {
                System.out.println("Enter a Genre for the game");
                System.out.println("All Genres: " + Arrays.toString(GameGenre.values()));
                getInput();
                clearScreen();
            }
        }
    }

    public void deleteGame(Game game) {
        System.out.println("Are you sure you want to delete " + game.getName() + "?");
        System.out.println("1. Yes");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                db.productsDB.removeProduct(game);
                System.out.println("Game deleted successfully");
                back = true;
            } else {
                System.out.println("Invalid input");
            }
            if (!back) {
                System.out.println("Are you sure you want to delete " + game.getName() + "?");
                System.out.println("1. Yes");
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
