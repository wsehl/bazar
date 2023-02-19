package repositories.interfaces;

import java.util.List;

import entities.Order;
import entities.Product;

public interface IOrderRepository {
    public int addOrder(Order order);

    public boolean insertOrderProducts(int orderId, List<Product> orderProducts);

    public List<Product> getProductsForOrder(int orderId);

    public Order getOrder(int orderId);

    public List<Order> getAllOrders();

    public List<Order> getOrdersForUser(int userId);
}
