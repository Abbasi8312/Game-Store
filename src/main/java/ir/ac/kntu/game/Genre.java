package ir.ac.kntu.game;

public enum Genre {
    ACTION,
    ADVENTURE,
    ROLE_PLAYING,
    STRATEGY,
    SIMULATION,
    SPORTS,
    PUZZLE,
    MMO,
    FPS,
    HORROR,
    SURVIVAL,
    FIGHTING,
    RACING,
    PLATFORMER;

    public static Genre find(String string) {
        for (Genre genre : values()) {
            if (string.equalsIgnoreCase(genre.name())) {
                return genre;
            }
        }
        return null;
    }
}