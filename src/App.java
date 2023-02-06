import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import controllers.AuthController;
import controllers.ProductController;
import exceptions.ObjectNotFoundException;
import models.Product;
import models.User;

public class App {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws Exception {

        ProductController productController = new ProductController();
        AuthController authController = new AuthController();

        System.out.print("Email: ");
        String email = br.readLine();

        System.out.print("Password: ");
        String password = br.readLine();

        // User user = authController.register("Valeriy", "B", "admin@example.com",
        // "123", 1);

        User user = authController.login(email, password);

        boolean isActive = false;

        if (user == null) {
            System.out.println("Wrong email or password!");
            return;
        } else {
            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
            isActive = true;
        }

        while (isActive) {
            int input = -1;

            System.out.println("Choose option:\n" +
                    "1 - Output product\n" +
                    "2 - Update product\n" +
                    "3 - Add product\n" +
                    "4 - Delete product\n" +
                    "5 - Close window");

            input = Integer.parseInt(br.readLine());

            // output product
            if (input == 1) {
                clearConsole();

                System.out.println("Choose option:\n" +
                        "1 - Output all products\n" +
                        "2 - Output product by ID\n" +
                        "3 - Back");

                switch (Integer.parseInt(br.readLine())) {
                    default:
                        System.out.println("Wront input!\n");
                        break;

                    // output all products
                    case 1: {
                        clearConsole();

                        List<Product> products = productController.getProducts();
                        if (products.size() < 1) {
                            System.out.println("Database is empty");
                            break;
                        }

                        products.forEach(System.out::println);
                        break;
                    }

                    // output product by id
                    case 2: {
                        clearConsole();
                        System.out.println("Enter product ID: ");
                        int productId = Integer.parseInt(br.readLine());
                        clearConsole();
                        try {
                            System.out.println(productController.getProductById(productId) + "\n");
                        } catch (ObjectNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }

                    case 3: {
                        clearConsole();
                    }
                }
            }

            // update product
            else if (input == 2) {
                clearConsole();
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.print("Enter product ID: ");
                productId = Integer.parseInt(br.readLine());
                clearConsole();

                try {
                    updatedProduct = productController.getProductById(productId);
                } catch (ObjectNotFoundException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                System.out.println("UPDATE PRODUCT: " + productId + "\nChoose option:\n" +
                        "1 - Update name\n" +
                        "2 - Update price\n" +
                        "3 - Update decription\n" +
                        "4 - Back");

                switch (Integer.parseInt(br.readLine())) {
                    default:
                        System.out.println("Wrong input!\n");
                        break;

                    // update name
                    case 1: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new name: ");
                        name = br.readLine();

                        updatedProduct.setName(name);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " name changed to \"" + name + "\"\n");
                        break;
                    }

                    // update price
                    case 2: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new price: ");
                        price = Double.parseDouble(br.readLine());

                        updatedProduct.setPrice(price);
                        productController.updateProduct(productId, updatedProduct);

                        System.out.println("Product " + productId + " price changed to \"" + price + "\"\n");
                        break;
                    }

                    // update description
                    case 3: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new description: ");
                        description = br.readLine();

                        updatedProduct.setDescription(description);
                        productController.updateProduct(productId, updatedProduct);

                        System.out
                                .println("Product " + productId + " description changed to \"" + description + "\"\n");
                        break;
                    }
                    case 4: {
                        clearConsole();
                    }
                }
            }

            // add product
            else if (input == 3) {
                clearConsole();

                System.out.print("Enter new product's name: ");
                String name = "";
                while (name == "") {
                    name = br.readLine();
                }

                System.out.print("Enter new product's price: ");
                double price = Double.parseDouble(br.readLine());

                System.out.print("Enter new product's description: ");
                String description = "";
                while (description == "") {
                    description = br.readLine();
                }

                Product product = new Product(name, description, price);
                int productId = productController.addProduct(product);
                clearConsole();
                System.out.println(
                        "Product \"" + name + "\"  has been added to database with id: " + productId + ".\n");
            }

            // delete product
            else if (input == 4) {
                clearConsole();
                System.out.print("Enter product id: ");
                int productId = Integer.parseInt(br.readLine());
                clearConsole();

                System.out.print("DELETE PRODUCT: " + productId + "\nAre you sure? (y/n): ");
                String confirmation = br.readLine();

                switch (confirmation) {
                    default:
                        System.out.println("Wrong input!\n");
                        break;

                    case "y": {
                        try {
                            productController.deleteProduct(productId);
                        } catch (ObjectNotFoundException e) {
                            clearConsole();
                            System.out.println(e.getMessage());
                            break;
                        }
                        clearConsole();
                        System.out.println("Product " + productId + " has been deleted successfully\n");
                    }

                    case "n": {
                        clearConsole();
                        break;
                    }
                }
            }

            // exit program
            else if (input == 5) {
                clearConsole();
                isActive = false;
                System.out.println("Marketplace CLI closed\n");
            }

            // wrong input
            else {
                clearConsole();
                System.out.println("Wrong input!\n");
            }
        }

        br.close();

    }

}