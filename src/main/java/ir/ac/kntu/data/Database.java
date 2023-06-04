package ir.ac.kntu.data;

import ir.ac.kntu.game.Game;
import ir.ac.kntu.user.Admin;
import ir.ac.kntu.user.User;
import ir.ac.kntu.utility.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database database;

    private final List<Game> games;

    private final List<User> userList;

    private final HashMap<String, User> users;

    private final HashMap<String, User> usersByPhone;

    private final HashMap<String, User> usersByEmail;

    private final Trie gameTrie;

    private final Trie userTrie;

    private Admin admin;

    private Database() {
        database = null;
        admin = new Admin("admin", "admin");
        games = new ArrayList<>();
        users = new HashMap<>();
        usersByPhone = new HashMap<>();
        usersByEmail = new HashMap<>();
        gameTrie = new Trie();
        userTrie = new Trie();
        userList = new ArrayList<>();
    }

    public static synchronized Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        if (user.getUsername() != null) {
            users.put(user.getUsername(), user);
            usersByPhone.put(user.getPhoneNumber(), user);
            usersByEmail.put(user.getEmail(), user);
            userList.add(user);
            userTrie.insert(user.getUsername(), userList.size() - 1);
        }
    }

    public void removeUser(User user) {
        for (User user1 : userList) {
            user1.removePendingRequest(user);
            user1.removeFriend(user);
        }
        userTrie.remove(user.getUsername(), userList.indexOf(user));
        users.remove(user.getUsername());
        userList.remove(user);
    }

    public void addGame(Game game) {
        games.add(game);
        gameTrie.insert(game.getName(), games.size() - 1);
    }

    public User findUserByUsername(String username) {
        return users.get(username);
    }

    public User findUserByPhoneNumber(String phone) {
        return usersByPhone.get(phone);
    }

    public User findUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public List<Game> filterGameByName(String string) {
        List<Integer> indexes = gameTrie.searchPrefix(string);
        List<Game> games = new ArrayList<>();
        for (Integer index : indexes) {
            games.add(this.games.get(index));
        }
        return games;
    }

    public List<Game> filterGameByPrice(double min, double max) {
        //TODO
        List<Game> tmp = new ArrayList<>();
        for (Game game : games) {
            if (game == null) {
                continue;
            }
            if (game.getPrice() >= min && game.getPrice() <= max) {
                tmp.add(game);
            }
        }
        return tmp;
    }

    public void changeUsername(String oldUsername, String newUsername) {
        users.put(newUsername, users.get(oldUsername));
        users.remove(oldUsername);
    }

    public void changePhoneNumber(String oldPhoneNumber, String newPhoneNumber) {
        usersByPhone.put(newPhoneNumber, usersByPhone.get(oldPhoneNumber));
        usersByPhone.remove(oldPhoneNumber);
    }

    public void changeEmail(String oldEmail, String newEmail) {
        usersByEmail.put(newEmail, usersByEmail.get(oldEmail));
        usersByEmail.remove(oldEmail);
    }

    public List<Game> getAllGames() {
        List<Game> gameList = new ArrayList<>();
        for (Game game : games) {
            if (game != null) {
                gameList.add(game);
            }
        }
        return gameList;
    }

    public Game findGame(String name) {
        for (Game game : games) {
            if (game.getName().equals(name)) {
                return game;
            }
        }
        return null;
    }

    public void removeGame(Game game) {
        for (User user : userList) {
            user.removeGame(game);
        }
        int index = games.indexOf(game);
        if (index != -1) {
            gameTrie.remove(game.getName(), index);
            games.set(index, null);
        }
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
