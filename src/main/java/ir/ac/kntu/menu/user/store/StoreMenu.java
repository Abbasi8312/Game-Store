package ir.ac.kntu.menu.user.store;

import ir.ac.kntu.model.Game;
import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.model.User2;
import ir.ac.kntu.utility.ConsoleCommand;
import ir.ac.kntu.utility.ErrorType;

public class StoreMenu extends UserMenu {
    public StoreMenu(User2 user) {
        super();
        currentUser = user;
    }

    public void storeGame(Game game) {
        System.out.println(game);
        boolean canAddGame = !currentUser.hasGame(game);
        if (canAddGame) {
            System.out.println("1. Buy game");
        }
        System.out.println("2. Gift game");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (canAddGame && input.equals("1")) {
                if (currentUser.buyGame(game)) {
                    System.out.println(game.getName() + " is added to your library");
                } else {
                    System.out.println("You don't have enough balance in your wallet to buy this game");
                }
                break;
            } else if (input.equals("2")) {
                giftGame(game);
                break;
            } else {
                System.out.println("Invalid input!");
            }
            System.out.println(game);
            if (canAddGame) {
                System.out.println("1. Buy game");
            }
            System.out.println("2. Gift game");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void giftGame(Game game) {
        System.out.println("Enter a username");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            User2 user = DB.findUserByUsername(input);
            if (user != null) {
                ErrorType errorType = currentUser.buyGame(game, user);
                if (errorType == ErrorType.NONE) {
                    System.out.println(game.getName() + " is added to user's library");
                } else if (errorType == ErrorType.NOT_ENOUGH_BALANCE){
                    System.out.println("You don't have enough balance in your wallet to buy this game");
                } else {
                    System.out.println("User already has this game");
                }
                break;
            } else {
                System.out.println("There is no user with this username");
            }
            System.out.println("Enter a username");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }
}
