package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.interfaces.IDB;
import entities.Order;
import entities.Product;
import exceptions.NoDatabaseConnectionException;
import repositories.interfaces.IOrderRepository;

public class OrderRepository implements IOrderRepository {
    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    public int addOrder(Order order) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "INSERT INTO orders (user_id, order_date) VALUES (?, ?) RETURNING order_id";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, order.getUserId());
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int lastId = resultSet.getInt("order_id");

                if (insertOrderProducts(lastId, order.getProducts())) {
                    return lastId;
                }
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

    public boolean insertOrderProducts(int orderId, List<Product> orderProducts) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Product product : orderProducts) {
                    statement.setInt(1, orderId);
                    statement.setInt(2, product.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
                return true;
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

        return false;
    }

    public List<Product> getProductsForOrder(int orderId) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT p.* FROM products p JOIN orders_products op ON p.product_id = op.product_id WHERE op.order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, orderId);

            List<Product> products = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                double productPrice = resultSet.getDouble("product_price");

                Product product = new Product(productId, productName, productDescription, productPrice);
                products.add(product);
            }

            return products;
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

        return null;
    }

    public Order getOrder(int orderId) {
        Connection connection = null;
        Order order = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                Date orderDate = resultSet.getDate("order_date");
                List<Product> products = getProductsForOrder(orderId);

                order = new Order(orderId, userId, orderDate, products);
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

        return order;
    }

    public List<Order> getAllOrders() {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int userId = resultSet.getInt("user_id");
                Date orderDate = resultSet.getDate("order_date");
                List<Product> products = getProductsForOrder(orderId);

                Order order = new Order(orderId, userId, orderDate, products);
                orders.add(order);
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

        return orders;
    }

    public List<Order> getOrdersForUser(int userId) {
        Connection connection = null;
        List<Order> orders = new ArrayList<>();

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM orders WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int orderUserId = resultSet.getInt("user_id");
                Date orderDate = resultSet.getDate("order_date");
                List<Product> products = getProductsForOrder(orderId);

                Order order = new Order(orderId, orderUserId, orderDate, products);
                orders.add(order);
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

        return orders;
    }
}
