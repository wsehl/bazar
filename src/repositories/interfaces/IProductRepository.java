package repositories.interfaces;

import entities.Product;

import java.util.List;

public interface IProductRepository {
    int createProduct(Product product);

    boolean updateProduct(int id, Product product);

    boolean deleteProduct(int id);

    Product getProduct(int id);

    List<Product> getProducts();

    List<Product> getProductsByName(String name);

    List<Product> getProductsByPrice(double start, double end);
}