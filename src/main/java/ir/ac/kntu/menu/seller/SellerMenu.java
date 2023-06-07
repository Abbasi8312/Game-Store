package ir.ac.kntu.menu.seller;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.model.role.AccessorySeller;

public class SellerMenu extends Menu {
    protected AccessorySeller currentSeller;

    public SellerMenu(DB db, AccessorySeller currentSeller) {
        super(db);
        this.currentSeller = currentSeller;
    }
}
