package ir.ac.kntu.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Product {
    private final Map<String, String> reviews;
    private String name;
    private double price;
    private String description;

    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        reviews = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getReviews() {
        return new HashMap<>(reviews);
    }

    public String getReview(String name) {
        return reviews.get(name);
    }

    public void addReview(String name, String review) {
        reviews.put(name, review);
    }

    public void removeReview(String name) {
        reviews.remove(name);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override public int hashCode() {
        return Objects.hash(name, price, description);
    }

    @Override public String toString() {
        return "Product{" + "reviews=" + reviews + ", name='" + name + '\'' + ", price=" + price + ", description='" +
                description + '\'' + '}';
    }
}
