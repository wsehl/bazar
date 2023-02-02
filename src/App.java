import controllers.ProductController;
import models.Product;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        ProductController productController = new ProductController();
        Scanner scanner = new Scanner(System.in);
        boolean isActive = true;

        while (isActive) {
            int input = 0;
            System.out.println("\nChoose option:\n" +
                                "1 - Output product\n" +
                                "2 - Update product\n" +
                                "3 - Add product\n" +
                                "4 - Delete product\n" +
                                "5 - Close window");
            input = scanner.nextInt();
            
            if (input == 1) { // output product
                System.out.println("Choose option:\n" + 
                                    "1 - Output all products\n" +
                                    "2 - Output product by ID");
                switch (scanner.nextInt()) {
                    default: System.out.println("Wront input!"); break;
                    case 1: { // output all products
                        productController.getProducts().forEach(System.out::println); 
                        break;
                    } 
                    case 2: { // output product by id
                        System.out.println("Enter product ID: ");
                        System.out.println(productController.getProductById(scanner.nextInt())); break;
                    }
                }
            } 
            else if (input == 2) { // update product
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.println("Enter product ID: ");
                productId = scanner.nextInt();
                System.out.println("Choose option:\n" +
                                    "1 - Update name\n" +
                                    "2 - Update price\n" +
                                    "3 - Update decription");

                switch (scanner.nextInt()) {
                    default: System.out.println("Wrong input!"); break;
                    case 1: { // update name
                        System.out.print("Enter new name: ");
                        name = "";
                        while (name == "") {
                            name = scanner.nextLine();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setName(name);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " name changed to \"" + name + "\"");
                        break;
                    }
                    case 2: { // update price
                        System.out.print("Enter new price: ");
                        price = -1;
                        while (price == -1) {
                            price = scanner.nextDouble();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setPrice(price);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " price changed to \"" + price + "\"");
                        break;
                    }
                    case 3: { // update description
                        System.out.print("Enter new description: ");
                        description = "";
                        while (description == "") {
                            description = scanner.nextLine();
                        }

                        updatedProduct = productController.getProductById(productId);
                        updatedProduct.setDescription(description);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " description changed to \"" + description + "\"");
                        break;
                    }
                }   
            }
            else if (input == 3) { // add product
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
                System.out.println("Product " + name + " has been added to database.");
            }
            else if (input == 4) { // delete product
                System.out.print("Enter product id: ");
                int productId = -1;
                while (productId == -1) {
                    productId = scanner.nextInt();
                }

                System.out.print("Are you sure? (y/n): ");
                String confirmation = "";
                while (confirmation == "") {
                    confirmation = scanner.nextLine();
                }

                switch (confirmation) {
                    default: System.out.println("Wrong input!"); break;
                    case "y": {
                        productController.deleteProduct(productId);
                        System.out.println("Product " + productId + " has been deleted successfully");
                    }
                    case "n": {
                        break;
                    }
                }
            }
            else if (input == 5) { // terminate loop
                isActive = false;
            }
            else {
                System.out.println("Wrong input!");
            }
        }

        scanner.close();

    }
}