package entities;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private List<Product> products;

    public Order(int userId, List<Product> products, Date orderDate) {
        this.userId = userId;
        this.products = products;
        this.orderDate = orderDate;
    }

    public Order(int orderId, int userId, Date orderDate, List<Product> products) {
        this(userId, products, orderDate);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order {" +
                "orderId = " + orderId +
                ", userId = " + userId +
                ", orderDate = " + orderDate +
                "}\nproducts = " + products;
    }
}
