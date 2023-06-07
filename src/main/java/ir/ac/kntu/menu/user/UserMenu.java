package ir.ac.kntu.menu.user;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.model.role.User;

public class UserMenu extends Menu {
    protected User currentUser;

    public UserMenu(DB db) {
        super(db);
    }
}
