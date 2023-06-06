package ir.ac.kntu.database;

public class DB {
    public final AccountsDB accountsDB;

    public final ProductsDB productsDB;

    public DB() {
        accountsDB = new AccountsDB(this);
        productsDB = new ProductsDB(this);
    }
}
