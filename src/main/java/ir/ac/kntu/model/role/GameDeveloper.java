package ir.ac.kntu.model.role;

import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;

import java.util.*;

public class GameDeveloper {
    public final Account account;

    private final Set<Game> games;

    private final List<Game> scheduledEvents;

    private final List<Game> inbox;

    public GameDeveloper(Account account) {
        this.account = account;
        games = new HashSet<>();
        scheduledEvents = new ArrayList<>();
        inbox = new ArrayList<>();
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

    public List<Game> getScheduledEvents() {
        return new ArrayList<>(scheduledEvents);
    }

    public void addScheduledEvent(Game game) {
        scheduledEvents.add(game);
    }

    public void removeScheduledEvent(Game game) {
        scheduledEvents.remove(game);
    }

    public List<Game> getInbox() {
        return new ArrayList<>(scheduledEvents);
    }

    public void addToInbox(Game game) {
        inbox.add(game);
    }

    public void removeFromInbox(Game game) {
        inbox.remove(game);
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
