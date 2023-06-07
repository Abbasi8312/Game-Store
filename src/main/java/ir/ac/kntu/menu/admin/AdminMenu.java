package ir.ac.kntu.menu.admin;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.model.role.Admin;

public class AdminMenu extends Menu {
    protected Admin currentAdmin;

    public AdminMenu(DB db, Admin admin) {
        super(db);
        currentAdmin = admin;
    }
}
