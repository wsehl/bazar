package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import interfaces.IOrderController;
import models.Controller;

public class OrderController extends Controller implements IOrderController {
    public OrderController() throws Exception {
    }

    public void createOrder(int userId, List<Integer> productIds) {
        try {
            getConnection().setAutoCommit(false);
            PreparedStatement statement = getConnection()
                    .prepareStatement("INSERT INTO orders (user_id, order_date) VALUES (?, ?) RETURNING order_id");
            statement.setInt(1, userId);
            statement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");

                for (int productId : productIds) {
                    PreparedStatement statement2 = getConnection()
                            .prepareStatement("INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)");
                    statement2.setInt(1, orderId);
                    statement2.setInt(2, productId);
                    statement2.executeUpdate();
                }
            }
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                getConnection().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void getOrdersByUserId(int userId) {
        try {
            PreparedStatement statement = getConnection()
                    .prepareStatement("SELECT * FROM orders WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Order ID: " + resultSet.getInt("order_id"));
                System.out.println("User ID: " + resultSet.getInt("user_id"));
                System.out.println("Order Date: " + resultSet.getDate("order_date"));
                System.out.println("Order Total: " + resultSet.getDouble("order_total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
