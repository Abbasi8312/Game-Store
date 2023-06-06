package ir.ac.kntu;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.auth.AuthMenu;
import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;

public class Main {

    public static void main(String[] args) {
        DB db = new DB();
        db.productsDB.addProduct(new Game("Assassin's Creed", 5.5, "1"));
        db.productsDB.addProduct(new Game("Assassin's Creed", 200.5, "2"));
        db.productsDB.addProduct(new Game("Assassin's Creed", 100.5, "3"));
        db.productsDB.addProduct(new Game("Dragon Age", 10, "4"));
        db.productsDB.addProduct(new Game("Witcher", 20, "5"));
        db.accountsDB.addAccount(new Account(db, "ali", "Abbasi12", "a@a.aa", "1"));
        db.accountsDB.addAccount(new Account(db, "Ali", "Abbasi12", "b@b.bb", "2"));
        new AuthMenu().mainOptions();
    }
}