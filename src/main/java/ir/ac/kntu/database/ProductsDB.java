package ir.ac.kntu.database;

import ir.ac.kntu.model.Account;
import ir.ac.kntu.model.Game;
import ir.ac.kntu.model.Product;
import ir.ac.kntu.utility.Trie;

import java.util.ArrayList;
import java.util.List;

public class ProductsDB {
    private final DB db;

    private final List<Product> productList;

    private final Trie prodcutTrie;

    public ProductsDB(DB db) {
        this.db = db;
        productList = new ArrayList<>();
        prodcutTrie = new Trie();
    }

    public void addProduct(Product product) {
        productList.add(product);
        prodcutTrie.insert(product.getName(), product.getReviews().size() - 1);
    }

    public List<Product> filterProductsByName(String string) {
        List<Integer> indexes = prodcutTrie.searchPrefix(string);
        List<Product> products = new ArrayList<>();
        for (Integer index : indexes) {
            products.add(productList.get(index));
        }
        return products;
    }

    public List<Product> filterProductsByPrice(double min, double max) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product != null && product.getPrice() >= min && product.getPrice() <= max) {
                products.add(product);
            }
        }
        return products;
    }


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    public void removeProduct(Product product) {
        for (Account account : db.accountsDB.getAccountList()) {
            if (product instanceof Game) {
                account.user.removeGame((Game) product);
            }
        }
        int index = productList.indexOf(product);
        if (index != -1) {
            prodcutTrie.remove(product.getName(), index);
            productList.set(index, null);
        }
    }
}
