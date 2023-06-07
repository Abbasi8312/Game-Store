package ir.ac.kntu.model.role;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.model.Accessory;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.Product;
import ir.ac.kntu.utility.ErrorType;
import ir.ac.kntu.utility.Trie;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class User {
    private static final double POINT_REFRESH_RATE_PER_SECOND = 5;

    public final Account account;

    private final Trie productTrie;

    private final Trie friendTrie;

    private final DB db;

    private final Map<String, User> friends;

    private final List<User> friendList;

    private final Set<User> pendingRequests;

    private final List<Product> products;

    private double walletBalance;

    private double points;

    private LocalTime startTime;

    public User(Account account) {
        this.account = account;
        productTrie = new Trie();
        friendTrie = new Trie();
        db = new DB();
        friends = new HashMap<>();
        friendList = new ArrayList<>();
        pendingRequests = new HashSet<>();
        products = new ArrayList<>();
        walletBalance = 0;
        points = 0;
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

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            if (product != null) {
                productList.add(product);
            }
        }
        return productList;
    }

    public void addProduct(Product product) {
        products.add(product);
        productTrie.insert(product.getName(), products.size() - 1);
    }

    private boolean checkLevel(Product product, User user) {
        if (product instanceof Game game) {
            double points;
            switch (game.getLevel()) {
                case 2 -> points = 20;
                case 3 -> points = 50;
                case 4 -> points = 100;
                default -> points = 0;
            }
            return user.points >= points;
        }
        return true;
    }

    private double applyDiscount() {
        if (points < 20) {
            return 1;
        }
        if (points < 50) {
            return 0.9;
        }
        if (points < 100) {
            return 0.8;
        }
        return 0.7;
    }

    public ErrorType buyProduct(Product product) {
        if (!checkLevel(product, this)) {
            return ErrorType.LOW_POINTS;
        }
        if (walletBalance >= product.getPrice()) {
            if (product instanceof Accessory && ((Accessory) product).getCount() > 0) {
                ((Accessory) product).setCount(((Accessory) product).getCount() - 1);
            } else if (product instanceof Accessory) {
                return ErrorType.ZERO_STOCK_COUNT;
            } else if (hasProduct(product)) {
                return ErrorType.ALREADY_HAS_GAME;
            }
            walletBalance -= product.getPrice() * applyDiscount();
            addProduct(product);
            return ErrorType.NONE;
        }
        return ErrorType.NOT_ENOUGH_BALANCE;
    }

    public ErrorType buyProduct(Product product, User user) {
        if (!checkLevel(product, user)) {
            return ErrorType.LOW_POINTS;
        }
        if (walletBalance >= product.getPrice()) {
            if (product instanceof Accessory && ((Accessory) product).getCount() > 0) {
                ((Accessory) product).setCount(((Accessory) product).getCount() - 1);
            } else if (product instanceof Accessory) {
                return ErrorType.ZERO_STOCK_COUNT;
            } else if (user.hasProduct(product)) {
                return ErrorType.ALREADY_HAS_GAME;
            }
            walletBalance -= product.getPrice() * applyDiscount();
            user.addProduct(product);
            return ErrorType.NONE;
        }
        return ErrorType.NOT_ENOUGH_BALANCE;
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }

    public List<Product> filterProductsByName(String string) {
        List<Integer> indexes = productTrie.searchPrefix(string);
        List<Product> products = new ArrayList<>();
        for (Integer index : indexes) {
            products.add(this.products.get(index));
        }
        return products;
    }

    public List<Product> filterProductsByPrice(double min, double max) {
        List<Product> tmp = new ArrayList<>();
        for (Product product : products) {
            if (product == null) {
                continue;
            }
            if (product.getPrice() >= min && product.getPrice() <= max) {
                tmp.add(product);
            }
        }
        return tmp;
    }

    public void removeGame(Game game) {
        int index = products.indexOf(game);
        if (index != -1) {
            productTrie.remove(game.getName(), index);
            products.set(index, null);
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

    public void login() {
        startTime = LocalTime.now();
    }

    public void logout() {
        points += Duration.between(startTime, LocalTime.now()).getSeconds() / POINT_REFRESH_RATE_PER_SECOND;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Double.compare(user.walletBalance, walletBalance) == 0 &&
                Objects.equals(productTrie, user.productTrie) && Objects.equals(friendTrie, user.friendTrie) &&
                Objects.equals(db, user.db) && Objects.equals(friends, user.friends) &&
                Objects.equals(friendList, user.friendList) && Objects.equals(pendingRequests, user.pendingRequests) &&
                Objects.equals(products, user.products);
    }

    @Override public int hashCode() {
        return Objects.hash(productTrie, friendTrie, db, friends, friendList, pendingRequests, products, walletBalance);
    }

    @Override public String toString() {
        return "User{" + "account=" + account + ", friendList=" + friendList + ", pendingRequests=" + pendingRequests +
                ", products=" + products + ", walletBalance=" + walletBalance + ", points=" + points + '}';
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
