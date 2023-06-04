package ir.ac.kntu.menu.user.options;

import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.menu.user.friends.FriendsMenu;
import ir.ac.kntu.menu.user.game.NextMenu;
import ir.ac.kntu.menu.user.game.SelectGame;
import ir.ac.kntu.menu.user.profile.ProfileMenu;
import ir.ac.kntu.model.User;
import ir.ac.kntu.utility.ConsoleCommand;

public class UserOptions extends UserMenu {
    public UserOptions(User user) {
        super();
        currentUser = user;
    }

    public void userOptions() {
        System.out.println("Enter a number.");
        System.out.println("1. Profile");
        System.out.println("2. Store");
        System.out.println("3. Library");
        System.out.println("4. Friends");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> new ProfileMenu(currentUser).profile();
                case "2" -> new SelectGame(NextMenu.STORE, currentUser, database.getAllGames()).store();
                case "3" -> new SelectGame(NextMenu.LIBRARY, currentUser, currentUser.getGames()).store();
                case "4" -> new FriendsMenu(currentUser).friendsOptions();
                default -> System.out.println("Wrong input!");
            }
            System.out.println("Enter a number.");
            System.out.println("1. Profile");
            System.out.println("2. Store");
            System.out.println("3. Library");
            System.out.println("4. Friends");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }
}
