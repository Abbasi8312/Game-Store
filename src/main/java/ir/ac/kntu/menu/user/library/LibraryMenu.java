package ir.ac.kntu.menu.user.library;

import ir.ac.kntu.model.Game;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.model.User;
import ir.ac.kntu.utility.ConsoleCommand;

public class LibraryMenu extends UserMenu {
    public LibraryMenu(User user) {
        super();
        currentUser = user;
    }

    public void gameOptions(Game game) {
        System.out.println(game);
        System.out.println("1. Community");
        System.out.println("2. Rate");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> gameCommunity(game);
                case "2" -> gameRate(game);
                default -> System.out.println("Invalid input");
            }
            System.out.println(game);
            System.out.println("1. Community");
            System.out.println("2. Rate");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    void gameCommunity(Game game) {
        for (String key : game.getAllReviews().keySet()) {
            System.out.println(key + ":");
            System.out.println(game.getReview(key));
        }
        System.out.println("Enter your review of the game");
        getInput();
        ConsoleCommand.clearScreen();
        if (canContinue()) {
            if (input.equals("")) {
                game.removeReview(currentUser.getUsername());
            } else {
                game.addReview(currentUser.getUsername(), input);
            }
        }
    }

    void gameRate(Game game) {
        System.out.println("Enter a number between 1 and 10 or just press enter to remove your rating");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.matches("[0-9]+(\\.[0-9]+)?")) {
                if (Double.parseDouble(input) >= 1 && Double.parseDouble(input) <= 10) {
                    game.rate(currentUser.getUsername(), Double.parseDouble(input));
                    break;
                } else {
                    System.out.println("Your rate should be between 1 and 10");
                }
            } else if (input.equals("")) {
                game.removeRate(currentUser.getUsername());
                break;
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("Enter a number between 1 and 10");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }
}
