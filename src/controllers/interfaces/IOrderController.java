package controllers.interfaces;

import java.util.List;

import entities.Order;
import entities.Product;

public interface IOrderController {
    public String placeOrder(int userId, List<Product> orderProducts);

    public List<Order> getOrdersForUser(int userId);
}
