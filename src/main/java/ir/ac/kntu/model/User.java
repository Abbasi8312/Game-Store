package ir.ac.kntu.model;

import ir.ac.kntu.database.Database;
import ir.ac.kntu.utility.ErrorType;
import ir.ac.kntu.utility.Trie;

import java.util.*;

public class User {
    private final Trie gameTrie;

    private final Trie friendTrie;

    private final Database database;

    private final Map<String, User> friends;

    private final List<User> friendList;

    private final Set<User> pendingRequests;

    private final List<Game> games;

    private String username;

    private String phoneNumber;

    private String email;

    private String password;

    private double walletBalance;

    public User() {
        database = Database.getDatabase();
        walletBalance = 0;
        games = new ArrayList<>();
        friends = new HashMap<>();
        pendingRequests = new HashSet<>();
        gameTrie = new Trie();
        friendTrie = new Trie();
        friendList = new ArrayList<>();
    }

    public User(String username, String password, String email, String phoneNumber) {
        database = Database.getDatabase();
        if (setUsername(username) != ErrorType.NONE) {
            this.username = null;
        }
        if (!setPassword(password)) {
            this.username = null;
        }
        if (setEmail(email) != ErrorType.NONE) {
            this.username = null;
        }
        if (setPhoneNumber(phoneNumber) != ErrorType.NONE) {
            this.username = null;
        }
        walletBalance = 0;
        games = new ArrayList<>();
        friends = new HashMap<>();
        pendingRequests = new HashSet<>();
        gameTrie = new Trie();
        friendTrie = new Trie();
        friendList = new ArrayList<>();
    }

    public boolean canLogin(String password) {
        return password.equals(this.password);
    }

    public String getUsername() {
        return username;
    }

    public ErrorType setUsername(String username) {
        if (database.findUserByUsername(username) != null) {
            return ErrorType.INDISTINCT;
        } else if (username.matches("^[A-Za-z][A-Za-z0-9 _-]*$")) {
            database.changeUsername(this.username, username);
            this.username = username;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ErrorType setPhoneNumber(String phoneNumber) {
        if (database.findUserByPhoneNumber(phoneNumber) != null) {
            return ErrorType.INDISTINCT;
        } else if (phoneNumber.matches("^\\+?\\d+$")) {
            database.changePhoneNumber(this.phoneNumber, phoneNumber);
            this.phoneNumber = phoneNumber;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getEmail() {
        return email;
    }

    public ErrorType setEmail(String email) {
        if (database.findUserByEmail(email) != null) {
            return ErrorType.INDISTINCT;
        } else if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            database.changeEmail(this.email, email);
            this.email = email;
            return ErrorType.NONE;
        } else {
            return ErrorType.NON_MATCHING;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (password.matches(".*[a-z].*")) {
            if (password.matches(".*[A-Z].*")) {
                if (password.matches(".*[0-9].*")) {
                    if (password.length() >= 8) {
                        this.password = password;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public void chargeWallet(double value) {
        walletBalance += value;
    }

    public List<Game> getGames() {
        List<Game> gameList = new ArrayList<>();
        for (Game game : games) {
            if (game != null) {
                gameList.add(game);
            }
        }
        return gameList;
    }

    public void addGame(Game game) {
        games.add(game);
        gameTrie.insert(game.getName(), games.size() - 1);
    }

    public boolean buyGame(Game game) {
        if (walletBalance >= game.getPrice()) {
            walletBalance -= game.getPrice();
            addGame(game);
            return true;
        }
        return false;
    }

    public ErrorType buyGame(Game game, User user) {
        if (user.hasGame(game)) {
            return ErrorType.ALREADY_HAS_GAME;
        } else if (walletBalance >= game.getPrice()) {
            walletBalance -= game.getPrice();
            user.addGame(game);
            return ErrorType.NONE;
        } else {
            return ErrorType.NOT_ENOUGH_BALANCE;
        }
    }

    public boolean hasGame(Game game) {
        return games.contains(game);
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

    public void removeGame(Game game) {
        int index = games.indexOf(game);
        if (index != -1) {
            gameTrie.remove(game.getName(), index);
            games.set(index, null);
        }
    }

    public List<User> getFriends() {
        return friendList;
    }

    private void addFriend(User user) {
        friends.put(user.getUsername(), user);
        friendList.add(user);
        friendTrie.insert(user.getUsername(), friendList.size() - 1);
    }

    public List<User> filterFriendsByName(String string) {
        List<Integer> indexes = friendTrie.searchPrefix(string);
        List<User> friends = new ArrayList<>();
        for (Integer index : indexes) {
            friends.add(friendList.get(index));
        }
        return friends;
    }

    public void removeFriend(User user) {
        friends.remove(user.getUsername());
        friendTrie.remove(user.getUsername(), friendList.indexOf(user));
        friendList.remove(user);
    }

    public boolean hasFriend(String username) {
        return friends.containsKey(username);
    }

    public Set<User> getPendingRequests() {
        return pendingRequests;
    }

    public void addPendingRequest(User user) {
        pendingRequests.add(user);
    }

    public void removePendingRequest(User user) {
        pendingRequests.remove(user);
    }

    public boolean hasPendingRequest(User user) {
        return pendingRequests.contains(user);
    }

    public void acceptFriendRequest(User user) {
        removePendingRequest(user);
        user.removePendingRequest(this);
        addFriend(user);
        user.addFriend(this);
    }

    public void declineFriendRequest(User user) {
        removePendingRequest(user);
    }

    @Override public String toString() {
        return String.format("Username: %s\nEmail address: %s\nPhone number: %s\nWallet balance: %f", username, email,
                phoneNumber, walletBalance);
    }
}
