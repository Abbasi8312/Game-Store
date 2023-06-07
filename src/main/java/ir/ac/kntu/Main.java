package ir.ac.kntu;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.auth.AuthMenu;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.ControllerType;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.GamingController;
import ir.ac.kntu.model.role.Role;

public class Main {

    public static void main(String[] args) {
        DB db = new DB();
        db.productsDB.addProduct(new Game("Assassin's Creed", 5.5, "1", 1));
        db.productsDB.addProduct(new Game("Assassin's Creed", 200.5, "2", 2));
        db.productsDB.addProduct(new Game("Assassin's Creed", 100.5, "3", 2));
        db.productsDB.addProduct(new Game("Dragon Age", 10, "4", 3));
        db.productsDB.addProduct(new Game("Witcher", 20, "5", 3));
        GamingController gc = new GamingController("XXXX", 50, "6", 6, "Something", ControllerType.WIRED);
        db.productsDB.addProduct(gc);
        Account account = new Account(db, "ali", "Abbasi12", "a@a.aa", "1");
        account.addRole(Role.USER);
        account.addRole(Role.ADMIN);
        db.accountsDB.addAccount(account);
        account = new Account(db, "Ali", "Abbasi12", "b@b.bb", "2");
        account.addRole(Role.USER);
        account.addRole(Role.GAME_DEVELOPER);
        account.addRole(Role.ACCESSORY_SELLER);
        db.accountsDB.addAccount(account);
        new AuthMenu(db).show();
    }
}