package entities;

import java.util.List;

import db.PostgresDB;
import repositories.ProductRepository;

public class Cart {
    
    private List<Product> products;
    private ProductRepository productRepository;

    public Cart() {
        productRepository = new ProductRepository(new PostgresDB());
    }

    public void addProduct(int productId) {
        Product product = productRepository.getProduct(productId);
        products.add(product);
    }

    public void removeProduct(int productId) {
        Product product = productRepository.getProduct(productId);
        products.remove(product);
    }

    @Override
    public String toString() {
        if (products.size() > 0) {
            StringBuilder productOutput = new StringBuilder();
            for (Product product : products) productOutput.append(product.toString()).append("\n");
            return productOutput.toString();
        } else {
            return "Cart is empty";
        }
    }

}
