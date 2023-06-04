package ir.ac.kntu;

import ir.ac.kntu.data.Database;
import ir.ac.kntu.game.Game;
import ir.ac.kntu.menu.auth.AuthMenu;
import ir.ac.kntu.user.User;

public class Main {

    public static void main(String[] args) {
        Database.getDatabase().addGame(new Game("Assassin's Creed", 5.5));
        Database.getDatabase().addGame(new Game("Assassin's Creed", 200.5));
        Database.getDatabase().addGame(new Game("Assassin's Creed", 100.5));
        Database.getDatabase().addGame(new Game("Dragon Age", 10));
        Database.getDatabase().addGame(new Game("Witcher", 20));
        Database.getDatabase().addUser(new User("ali", "Abbasi12", "a@a.aa", "1"));
        Database.getDatabase().addUser(new User("Ali", "Abbasi12", "b@b.bb", "2"));
        new AuthMenu().mainOptions();
    }
}