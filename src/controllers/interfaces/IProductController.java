package controllers.interfaces;

import entities.Product;

public interface IProductController {
    String addProduct(Product product);

    String deleteProduct(int id);

    String updateProduct(int id, Product product);

    Product getProduct(int id);

    String getAllProducts();

    String getProductsByName(String name);

    String getProductsByPrice(double start, double end);
}
