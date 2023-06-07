package ir.ac.kntu.model.role;

import ir.ac.kntu.model.Accessory;
import ir.ac.kntu.model.Account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccessorySeller {
    public final Account account;

    private final Set<Accessory> accessories;

    public AccessorySeller(Account account) {
        this.account = account;
        accessories = new HashSet<>();
    }


    public List<Accessory> getAccessories() {
        return new ArrayList<>(accessories);
    }

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
    }

    public void removeAccessory(Accessory accessory) {
        accessories.remove(accessory);
    }
}
