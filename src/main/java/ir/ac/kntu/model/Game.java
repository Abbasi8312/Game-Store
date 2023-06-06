package ir.ac.kntu.model;

import java.util.HashMap;
import java.util.Map;

public class Game extends Product {
    private final Map<String, Double> rates;

    private GameGenre gameGenre;

    private double rating;

    public Game(String name, double price, String description) {
        super(name, price, description);
        rating = 0;
        rates = new HashMap<>();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
        rates.clear();
        rates.put(null, rating);
    }

    public void rate(String username, double value) {
        rating = rating * rates.size() - (rates.containsKey(username) ? rates.get(username) : 0) + value;
        rates.put(username, value);
        rating = rating / rates.size();
    }

    public void removeRate(String username) {
        rating = rating * rates.size() - (rates.containsKey(username) ? rates.get(username) : 0);
        rates.remove(username);
        if (rates.size() != 0) {
            rating = rating / rates.size();
        } else {
            rating = 0;
        }
    }

    public GameGenre getGenre() {
        return gameGenre;
    }

    public void setGenre(GameGenre gameGenre) {
        this.gameGenre = gameGenre;
    }
}
