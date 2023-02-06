import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import controllers.ProductController;
import exceptions.*;
import models.Product;

public class App {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        
        ProductController productController = new ProductController();
        
        Scanner scanner = new Scanner(System.in);
        clearConsole();

        System.out.print("Login: ");
        String login = br.readLine();

        System.out.print("Password: ");
        String password = br.readLine();

        boolean isActive = (login.equals("0") && password.equals("0")) ? true : false;
        clearConsole();
        
        while (isActive) {
            int input = -1;
            System.out.println("Choose option:\n" +
                    "1 - Output product\n" +
                    "2 - Update product\n" +
                    "3 - Add product\n" +
                    "4 - Delete product\n" +
                    "5 - Close window");
            input = scanner.nextInt();

            if (input == 1) { // output product
                clearConsole();
                System.out.println("Choose option:\n" +
                        "1 - Output all products\n" +
                        "2 - Output product by ID\n" +
                        "3 - Back");
                switch (scanner.nextInt()) {
                    default:
                        System.out.println("Wront input!\n");
                        break;
                    case 1: { // output all products
                        clearConsole();
                        List<Product> products = productController.getProducts();
                        if (products.size() < 1) {
                            System.out.println("Database is empty");
                            break;
                        }
                        products.forEach(System.out::println);
                        break;
                    }
                    case 2: { // output product by id
                        clearConsole();
                        System.out.println("Enter product ID: ");
                        int productId = scanner.nextInt();
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
            } else if (input == 2) { // update product
                clearConsole();
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.print("Enter product ID: ");
                productId = scanner.nextInt();
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

                switch (scanner.nextInt()) {
                    default:
                        System.out.println("Wrong input!\n");
                        break;
                    case 1: { // update name
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new name: ");
                        name = br.readLine();
                        
                        updatedProduct.setName(name);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " name changed to \"" + name + "\"\n");
                        break;
                    }
                    case 2: { // update price
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new price: ");
                        price = scanner.nextDouble();

                        updatedProduct.setPrice(price);
                        productController.updateProduct(productId, updatedProduct);

                        System.out.println("Product " + productId + " price changed to \"" + price + "\"\n");
                        break;
                    }
                    case 3: { // update description
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new description: ");
                        description = br.readLine();

                        updatedProduct.setDescription(description);
                        productController.updateProduct(productId, updatedProduct);

                        System.out.println("Product " + productId + " description changed to \"" + description + "\"\n");
                        break;
                    }
                    case 4: {
                        clearConsole();
                    }
                }
            } else if (input == 3) { // add product
                clearConsole();

                System.out.print("Enter new product's name: ");
                String name = "";
                while (name == "") {
                    name = scanner.nextLine();
                }

                System.out.print("Enter new product's price: ");
                double price = scanner.nextDouble();

                System.out.print("Enter new product's description: ");
                String description = "";
                while (description == "") {
                    description = scanner.nextLine();
                }

                Product product = new Product(name, description, price);
                productController.addProduct(product);
                clearConsole();
                // fix getId in class Product
                System.out.println("Product \"" + name + "\"  has been added to database with id: " + product.getId() + ".\n");
            } else if (input == 4) { // delete product
                clearConsole();
                System.out.print("Enter product id: ");
                int productId = scanner.nextInt();
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
            } else if (input == 5) { // terminate loop
                clearConsole();
                isActive = false;
                System.out.println("Marketplace CLI closed\n");
            } else {
                clearConsole();
                System.out.println("Wrong input!\n");
            }
        }
        scanner.close();

    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}