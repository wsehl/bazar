package controllers;

import java.util.List;

import controllers.interfaces.IProductController;
import entities.Product;
import repositories.ProductRepository;

public class ProductController implements IProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String addProduct(Product product) {
        int id = productRepository.createProduct(product);

        if (id == -1)
            return "Product could not be added";

        return "Product added with id: " + id;
    }

    public String updateProduct(int id, Product product) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        boolean updated = productRepository.updateProduct(id, product);

        if (!updated)
            return "Product " + id + " could not be updated";

        return "Product" + id + "updated";
    }

    public String deleteProduct(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        boolean deleted = productRepository.deleteProduct(id);

        if (!deleted)
            return "Product could not be deleted";

        return "Product deleted";
    }

    public Product getProduct(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        Product foundProduct = productRepository.getProduct(id);

        if (foundProduct == null)
            System.out.println("Product not found");

        return foundProduct;
    }

    public String getAllProducts() {
        List<Product> products = productRepository.getProducts();

        if (products.size() == 0) {
            return "No products found";
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getProductsByName(String name) {
        List<Product> products = productRepository.getProductsByName(name);

        if (products.size() == 0) {
            return "No products found";
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getProductsByPrice(double start, double end) {
        List<Product> products = productRepository.getProductsByPrice(start, end);

        if (products.size() == 0) {
            return "No products found";
        }

        StringBuilder sb = new StringBuilder();

        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }

        return sb.toString();
    }
}
