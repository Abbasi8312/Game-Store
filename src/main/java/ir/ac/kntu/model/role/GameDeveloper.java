package ir.ac.kntu.model.role;

import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;

import java.util.*;

public class GameDeveloper {
    public final Account account;

    private final Set<Game> games;

    public GameDeveloper(Account account) {
        this.account = account;
        games = new HashSet<>();
    }

    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public boolean addDeveloper(Game game, GameDeveloper gameDeveloper) {
        for (Game game1 : games) {
            if (game1.equals(game)) {
                game.addGameDeveloper(gameDeveloper);
                return true;
            }
        }
        return false;
    }

    @Override public String toString() {
        return "GameDeveloper{" + "games=" + games + '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameDeveloper that = (GameDeveloper) o;
        return Objects.equals(games, that.games) && Objects.equals(account, that.account);
    }

    @Override public int hashCode() {
        return Objects.hash(games, account);
    }
}
