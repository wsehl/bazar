import cli.Application;
import controllers.ProductController;
import controllers.UserController;
import db.PostgresDB;
import db.interfaces.IDB;
import exceptions.NoDatabaseConnectionException;
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

            Application application = new Application(userController, productController);

            application.run();

        } catch (NoDatabaseConnectionException e) {
            System.out.println("No database connection. App will be closed.");
        }
    }
}