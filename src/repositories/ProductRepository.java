package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.IDB;
import entities.Product;
import exceptions.NoDatabaseConnectionException;
import repositories.interfaces.IProductRepository;

public class ProductRepository implements IProductRepository {
    private final IDB db;

    public ProductRepository(IDB db) {
        this.db = db;
    }

    public int createProduct(Product product) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "INSERT INTO products (product_name, product_description, product_price) VALUES (?, ?, ?) RETURNING product_id";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("product_id");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return -1;
    }

    public boolean updateProduct(int id, Product updatedProduct) {
        Connection connection = null;

        try {
            connection = db.getConnection();
            String query = "UPDATE products SET product_name = ?, product_description = ?, product_price = ? WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, updatedProduct.getName());
            statement.setString(2, updatedProduct.getDescription());
            statement.setDouble(3, updatedProduct.getPrice());
            statement.setInt(4, id);

            statement.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public boolean deleteProduct(int id) {
        Connection connection = null;

        try {
            connection = db.getConnection();
            String query = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            statement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public Product getProduct(int id) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

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
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public List<Product> getProducts() {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");

                products.add(new Product(productId, productName, productDescription, productPrice));
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public List<Product> getProductsByName(String name) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM products WHERE product_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");

                Product product = new Product(productId, productName, productDescription, productPrice);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public List<Product> getProductsByPrice(double start, double end) {
        Connection connection = null;

        try {
            connection = db.getConnection();
            String query = "SELECT * FROM products WHERE product_price >= ? and product_price <= ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDouble(1, start);
            statement.setDouble(2, end);

            ResultSet resultSet = statement.executeQuery();

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");

                Product product = new Product(productId, productName, productDescription, productPrice);

                products.add(product);

                return products;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        // List<Product> products = new ArrayList<>();
        // try {
        // PreparedStatement statement = db.getConnection()
        // .prepareStatement("SELECT * FROM products WHERE product_price >= ? and
        // product_price <= ?");
        // statement.setDouble(1, start);
        // statement.setDouble(2, end);
        // ResultSet resultSet = statement.executeQuery();

        // while (resultSet.next()) {
        // int productId = resultSet.getInt("product_id");
        // String productName = resultSet.getString("product_name");
        // String productDescription = resultSet.getString("product_description");
        // double productPrice = resultSet.getDouble("product_price");
        // products.add(new Product(productId, productName, productDescription,
        // productPrice));
        // }
        // } catch (SQLException e) {
        // e.printStackTrace();
        // }

        return null;
    }

}
