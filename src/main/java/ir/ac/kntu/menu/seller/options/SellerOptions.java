package ir.ac.kntu.menu.seller.options;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.profile.ProfileMenu;
import ir.ac.kntu.menu.seller.SellerMenu;
import ir.ac.kntu.model.role.AccessorySeller;

public class SellerOptions extends SellerMenu {
    public SellerOptions(DB db, AccessorySeller accessorySeller) {
        super(db, accessorySeller);
    }

    public void sellerOptions() {
        System.out.println("1. Profile");
        System.out.println("2. New accessory");
        System.out.println("3. Modify accessory");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                new ProfileMenu(db).changeProfile(currentSeller.account);
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("1. Profile");
            System.out.println("2. New accessory");
            System.out.println("3. Modify accessory");
            getInput();
            clearScreen();
        }
    }
}
