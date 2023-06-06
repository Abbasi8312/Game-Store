package ir.ac.kntu.model;

public enum GameGenre {
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

    public static GameGenre find(String string) {
        for (GameGenre gameGenre : values()) {
            if (string.equalsIgnoreCase(gameGenre.name())) {
                return gameGenre;
            }
        }
        return null;
    }
}