package ir.ac.kntu.menu.developer;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.model.role.GameDeveloper;

public class DeveloperMenu extends Menu {
    protected GameDeveloper currentDeveloper;

    public DeveloperMenu(DB db, GameDeveloper gameDeveloper) {
        super(db);
        currentDeveloper = gameDeveloper;
    }
}
