package ir.ac.kntu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game extends Product {
    private final Map<String, Double> rates;

    private GameGenre gameGenre;

    private double rating;

    public Game(String name, double price, String description) {
        super(name, price, description);
        rating = 0;
        rates = new HashMap<>();
    }

    public Game() {
        super();
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

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Game game = (Game) o;
        return Double.compare(game.rating, rating) == 0 && Objects.equals(rates, game.rates) &&
                gameGenre == game.gameGenre;
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), rates, gameGenre, rating);
    }

    @Override public String toString() {
        return super.toString() + "\nGame{" + "rates=" + rates + ", gameGenre=" + gameGenre + ", rating=" + rating +
                '}';
    }
}
