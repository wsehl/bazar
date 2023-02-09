package interfaces;

import java.util.List;

public interface IOrderController {
    public void createOrder(int userId, List<Integer> productIds);
    public void getOrdersByUserId(int userId);
}
