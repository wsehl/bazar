import cli.Application;
import controllers.OrderController;
import controllers.ProductController;
import controllers.UserController;
import db.PostgresDB;
import db.interfaces.IDB;
import exceptions.NoDatabaseConnectionException;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.UserRepository;

public class App {
    public static void main(String[] args) {
        IDB postgresDb = new PostgresDB();

        try {
            postgresDb.getConnection();

            UserRepository userRepository = new UserRepository(postgresDb);
            UserController userController = new UserController(userRepository);

            ProductRepository productRepository = new ProductRepository(postgresDb);
            ProductController productController = new ProductController(productRepository);

            OrderRepository orderRepository = new OrderRepository(postgresDb);
            OrderController orderController = new OrderController(orderRepository);

            Application app = new Application(userController, productController,
                    orderController);

            app.run();
        } catch (NoDatabaseConnectionException e) {
            System.out.println("No database connection. App will be closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}