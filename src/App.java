import controllers.ProductController;
import models.Product;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        ProductController productController = new ProductController();
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;
        clearConsole();

        while (isActive) {
            int input = 0;
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
                    default: System.out.println("Wront input!\n"); break;
                    case 1: { // output all products
                        clearConsole();
                        productController.getProducts().forEach(System.out::println); 
                        break;
                    } 
                    case 2: { // output product by id
                        clearConsole();
                        System.out.println("Enter product ID: ");
                        int productId = -1;
                        while (productId == -1) {
                            productId = scanner.nextInt();
                        }
                        clearConsole();
                        System.out.println(productController.getProductById(productId) + "\n"); break;
                    }
                    case 3: {
                        clearConsole();
                    }
                }
            } 
            else if (input == 2) { // update product
                clearConsole();
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.print("Enter product ID: ");
                productId = scanner.nextInt();
                clearConsole();
                System.out.println("UPDATE PRODUCT: " + productId + "\nChoose option:\n" +
                                    "1 - Update name\n" +
                                    "2 - Update price\n" +
                                    "3 - Update decription\n" +
                                    "4 - Back");

                switch (scanner.nextInt()) {
                    default: System.out.println("Wrong input!\n"); break;
                    case 1: { // update name
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new name: ");
                        name = "";
                        while (name == "") {
                            name = scanner.nextLine();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setName(name);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " name changed to \"" + name + "\"\n");
                        break;
                    }
                    case 2: { // update price
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new price: ");
                        price = -1;
                        while (price == -1) {
                            price = scanner.nextDouble();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setPrice(price);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " price changed to \"" + price + "\"\n");
                        break;
                    }
                    case 3: { // update description
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId + "\nEnter new description: ");
                        description = "";
                        while (description == "") {
                            description = scanner.nextLine();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setDescription(description);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " description changed to \"" + description + "\"\n");
                        break;
                    }
                    case 4: {
                        clearConsole();
                    }
                }   
            }
            else if (input == 3) { // add product
                clearConsole();
                System.out.print("Enter new product's name: ");
                String name = "";
                while (name == "") {
                    name = scanner.nextLine();
                }

                System.out.print("Enter new product's price: ");
                double price = -1;
                while (price == -1) {
                    price = scanner.nextDouble();
                }

                System.out.print("Enter new product's description: ");
                String description = "";
                while (description == "") {
                    description = scanner.nextLine();
                }

                Product product = new Product(name, description, price);
                productController.addProduct(product);
                clearConsole();

                System.out.println("Product \"" + name + "\"  has been added to database.\n");
            }
            else if (input == 4) { // delete product
                clearConsole();
                System.out.print("Enter product id: ");
                int productId = -1;
                while (productId == -1) {
                    productId = scanner.nextInt();
                }

                System.out.print("DELETE PRODUCT: " + productId + "\nAre you sure? (y/n): ");
                String confirmation = "";
                while (confirmation == "") {
                    confirmation = scanner.nextLine();
                }

                switch (confirmation) {
                    default: System.out.println("Wrong input!\n"); break;
                    case "y": {
                        productController.deleteProduct(productId);
                        clearConsole();
                        System.out.println("Product " + productId + " has been deleted successfully\n");
                    }
                    case "n": {
                        clearConsole();
                        break;
                    }
                }
            }
            else if (input == 5) { // terminate loop
                clearConsole();
                isActive = false;
                System.out.println("Marketplace CLI closed\n");
            }
            else {
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