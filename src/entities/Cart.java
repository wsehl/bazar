package entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public boolean isInCart(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId())
                return true;
        }

        return false;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.removeIf(p -> Integer.valueOf(p.getId()).equals(product.getId()));
    }

    public double getTotalPrice() {
        double totalPrice = 0;

        for (Product product : products)
            totalPrice += product.getPrice();

        return totalPrice;
    }

    @Override
    public String toString() {
        if (products.size() > 0) {
            StringBuilder productOutput = new StringBuilder();
            for (Product product : products)
                productOutput.append(product.toString()).append("\n");

            return productOutput.toString() + "Total price: " + getTotalPrice();
        } else {
            return "Cart is empty";
        }
    }
}