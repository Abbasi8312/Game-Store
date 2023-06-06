package ir.ac.kntu.model;

import ir.ac.kntu.model.role.AccessorySeller;
import ir.ac.kntu.model.role.Admin;
import ir.ac.kntu.model.role.GameDeveloper;
import ir.ac.kntu.model.role.User;

public abstract class AccountRoles {
    public final User user;

    public final AccessorySeller accessorySeller;

    public final GameDeveloper gameDeveloper;

    public final Admin admin;

    public AccountRoles() {
        user = new User((Account) this);
        accessorySeller = new AccessorySeller();
        gameDeveloper = new GameDeveloper();
        admin = new Admin((Account) this);
    }
}
