package ir.ac.kntu.model;

import ir.ac.kntu.model.role.*;

import java.util.HashSet;
import java.util.Set;

public abstract class AccountRoles {
    public final User user;

    public final AccessorySeller accessorySeller;

    public final GameDeveloper gameDeveloper;

    public final Admin admin;

    private final Set<Role> roles;

    public AccountRoles() {
        user = new User((Account) this);
        accessorySeller = new AccessorySeller((Account) this);
        gameDeveloper = new GameDeveloper((Account) this);
        admin = new Admin((Account) this);
        roles = new HashSet<>();
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}
