package ir.ac.kntu.model;

public abstract class Accessory extends Product {
    private int count;

    public Accessory(String name, double price, String description, int count) {
        super(name, price, description);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
