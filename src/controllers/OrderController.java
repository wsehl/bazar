package controllers;

import java.util.Date;
import java.util.List;

import controllers.interfaces.IOrderController;
import entities.Order;
import entities.Product;
import repositories.OrderRepository;

public class OrderController implements IOrderController {
    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String placeOrder(int userId, List<Product> orderProducts) {
        Order order = new Order(userId, orderProducts, new Date());

        if (orderProducts.size() == 0)
            return "Order could not be added (no products in cart)\n";

        int id = orderRepository.addOrder(order);

        if (id == -1)
            return "Order could not be added";

        return "Order added with id: " + id;
    }

    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.getOrdersForUser(userId);
    }
}
