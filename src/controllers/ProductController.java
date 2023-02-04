package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ObjectNotFoundException;
import models.Product;
import models.Controller;

public class ProductController extends Controller {

    public ProductController() throws Exception {}

    public void addProduct(Product product) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "INSERT INTO products (product_name, product_description, product_price) VALUES (?, ?, ?)");
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM products");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");
                products.add(new Product(productId, productName, productDescription, productPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int id) throws ObjectNotFoundException {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM products WHERE product_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");
                return new Product(productId, productName, productDescription, productPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ObjectNotFoundException("Product " + id + " wasn't found");
    }

    public void updateProduct(int id, Product updatedProduct) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(
                    "UPDATE products SET product_name = ?, product_description = ?, product_price = ? WHERE product_id = ?");
            statement.setString(1, updatedProduct.getName());
            statement.setString(2, updatedProduct.getDescription());
            statement.setDouble(3, updatedProduct.getPrice());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) throws ObjectNotFoundException {
        try { // make deleteProduct(Product product), not (int id)
            Product product = getProductById(id); 
            PreparedStatement statement = getConnection().prepareStatement("DELETE FROM products WHERE product_id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ObjectNotFoundException("Product " + id + " wasn't found");
        }
    }
}
