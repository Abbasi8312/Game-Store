package ir.ac.kntu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game {
    private final Map<String, Double> rates;

    private final Map<String, String> reviews;

    private String name;

    private double price;

    private String description;

    private Genre genre;

    private double rating;

    public Game() {
        rating = 0;
        rates = new HashMap<>();
        reviews = new HashMap<>();
    }

    public Game(String name, double price) {
        this.name = name;
        this.price = price;
        rating = 0;
        rates = new HashMap<>();
        reviews = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public HashMap<String, String> getAllReviews() {
        return new HashMap<>(reviews);
    }

    public String getReview(String key) {
        return reviews.get(key);
    }

    public void addReview(String username, String review) {
        reviews.put(username, review);
    }

    public void removeReview(String username) {
        reviews.remove(username);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override public String toString() {
        return String.format("Name: %s\nPrice: %f\nDescription: %s\nGenre: %s\nRating: %s\n", name, price, description,
                genre, rating);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return Double.compare(game.price, price) == 0 && Double.compare(game.rating, rating) == 0 &&
                Objects.equals(rates, game.rates) && Objects.equals(reviews, game.reviews) &&
                Objects.equals(name, game.name) && Objects.equals(description, game.description) && genre == game.genre;
    }

    @Override public int hashCode() {
        return Objects.hash(rates, reviews, name, price, description, genre, rating);
    }
}
