package ir.ac.kntu.model.role;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.utility.ErrorType;
import ir.ac.kntu.utility.Trie;

import java.util.*;

public class User {
    private final Account account;

    private final Trie gameTrie;

    private final Trie friendTrie;

    private final DB db;

    private final Map<String, User> friends;

    private final List<User> friendList;

    private final Set<User> pendingRequests;

    private final List<Game> games;

    private double walletBalance;

    public User(Account account) {
        this.account = account;
        gameTrie = new Trie();
        friendTrie = new Trie();
        db = new DB();
        friends = new HashMap<>();
        friendList = new ArrayList<>();
        pendingRequests = new HashSet<>();
        games = new ArrayList<>();
        walletBalance = 0;
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
        friends.put(user.account.getName(), user);
        friendList.add(user);
        friendTrie.insert(user.account.getName(), friendList.size() - 1);
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
        friends.remove(user.account.getName());
        friendTrie.remove(user.account.getName(), friendList.indexOf(user));
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

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Double.compare(user.walletBalance, walletBalance) == 0 && Objects.equals(gameTrie, user.gameTrie) &&
                Objects.equals(friendTrie, user.friendTrie) && Objects.equals(db, user.db) &&
                Objects.equals(friends, user.friends) && Objects.equals(friendList, user.friendList) &&
                Objects.equals(pendingRequests, user.pendingRequests) && Objects.equals(games, user.games);
    }

    @Override public int hashCode() {
        return Objects.hash(gameTrie, friendTrie, db, friends, friendList, pendingRequests, games, walletBalance);
    }

    @Override public String toString() {
        return "User{" + "friends=" + friends + ", friendList=" + friendList + ", pendingRequests=" + pendingRequests +
                ", games=" + games + ", walletBalance=" + walletBalance + '}';
    }
}
