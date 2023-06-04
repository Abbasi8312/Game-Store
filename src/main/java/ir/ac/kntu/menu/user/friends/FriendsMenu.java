package ir.ac.kntu.menu.user.friends;

import ir.ac.kntu.menu.user.UserMenu;
import ir.ac.kntu.menu.user.game.SelectGame;
import ir.ac.kntu.model.User;
import ir.ac.kntu.utility.ConsoleCommand;

import java.util.ArrayList;
import java.util.List;

public class FriendsMenu extends UserMenu {
    public FriendsMenu(User user) {
        super();
        currentUser = user;
    }

    public void friendsOptions() {
        System.out.println("1. Show all friends");
        System.out.println("2. Search friends by username");
        System.out.println("3. Add friend");
        System.out.println("4. Friend requests");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> friendsShowAll();
                case "2" -> filterFriendsByName();
                case "3" -> addFriend();
                case "4" -> friendRequests();
                default -> System.out.println("Invalid input");
            }
            System.out.println("1. Show all friends");
            System.out.println("2. Search friends by username");
            System.out.println("3. Add friend");
            System.out.println("4. Friend requests");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void friendsShowAll() {
        selectUser(currentUser.getFriends(), NextMenu.GAME);
    }

    public void addFriend() {
        System.out.println("Enter username");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            User user = database.findUserByUsername(input);
            if (user == null) {
                System.out.println("This username doesn't exist");
            } else if (user.equals(currentUser)) {
                System.out.println("You can't send a friend request to yourself");
            } else if (user.hasFriend(currentUser.getUsername())) {
                System.out.println("You are already friends with this user");
                break;
            } else if (user.hasPendingRequest(currentUser)) {
                System.out.println("You have already sent a friend request to " + input);
                break;
            } else {
                user.addPendingRequest(currentUser);
                System.out.println("Friend request sent");
                break;
            }
            System.out.println("Enter username");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void filterFriendsByName() {
        System.out.println("What do you want to search?");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            selectUser(currentUser.filterFriendsByName(input), NextMenu.GAME);
            System.out.println("What do you want to search?");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void friendRequests() {
        selectUser(new ArrayList<>(currentUser.getPendingRequests()), NextMenu.REQUEST);
    }

    public void answerRequest(User user) {
        System.out.println("1. Accept");
        System.out.println("2. Decline");
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            switch (input) {
                case "1" -> {
                    currentUser.acceptFriendRequest(user);
                    return;
                }
                case "2" -> {
                    currentUser.declineFriendRequest(user);
                    return;
                }
                default -> System.out.println("Invalid input");
            }
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    public void selectUser(List<User> users, NextMenu nextMenu) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + 1 + ". Username: " + users.get(i).getUsername());
        }
        getInput();
        ConsoleCommand.clearScreen();
        while (canContinue()) {
            if (input.matches("^[0-9]+$") && Integer.parseInt(input) > 0 && Integer.parseInt(input) <= users.size()) {
                if (nextMenu == NextMenu.GAME) {
                    new SelectGame(ir.ac.kntu.menu.user.game.NextMenu.NONE, users.get(Integer.parseInt(input) - 1),
                            users.get(Integer.parseInt(input) - 1).getGames()).store();
                } else {
                    answerRequest(users.get(Integer.parseInt(input) - 1));
                    break;
                }
            } else {
                System.out.println("Invalid input");
            }
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". Username: " + users.get(i).getUsername());
            }
            getInput();
            ConsoleCommand.clearScreen();
        }
    }

    enum NextMenu {
        GAME,
        REQUEST
    }
}
