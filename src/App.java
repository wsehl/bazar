import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.AuthController;
import controllers.ProductController;
import exceptions.ObjectNotFoundException;
import exceptions.UserAlreadyExistsException;
import models.Product;
import models.User;

public class App {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_STRENGTH_REGEX = Pattern
            .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\S]{8,10}$");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        return true;
    }

    public static boolean getPasswordStrength(String password) {
        Matcher matcher = PASSWORD_STRENGTH_REGEX.matcher(password);
        return matcher.find();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String getStrengthBar(int percent) {
        char barCompleteChar = '\u2588';
        char barIncompleteChar = '\u2591';

        String bar = "";

        for (int i = 0; i < 10; i++) {
            if (i < percent / 10) {
                bar += barCompleteChar;
            } else {
                bar += barIncompleteChar;
            }
        }

        return bar;
    }

    public static void main(String[] args) throws Exception {

        ProductController productController = new ProductController();
        AuthController authController = new AuthController();
        Scanner scanner = new Scanner(System.in);
        User user = null;
        boolean isActive = false;
        clearConsole();
        System.out.println("████████████████████████████████\n" +
                "████████████████████████████████\n" +
                "██░░░░██████░░░░████░░░░░░░░░░██\n" +
                "██░░░░░░██░░██████████░░████░░██\n" +
                "██░░░░██░░██░░░░████░░░░░░░░░░██\n" +
                "██░░░░██████░░░░████░░░░████████\n" +
                "██░░░░██████░░░░████░░░░████████\n" +
                "████████████████████████████████\n" +
                "████████████████████████████████");
        System.out.println("________________________________");
        System.out.println("\nWelcome to bazar!©");
        System.out.println("________________________________\n");
        while (isActive == false) {
            int input = -1;
            System.out.println("Choose option:\n" +
                    "1 - Sign In\n" +
                    "2 - Sign Up");

            input = scanner.nextInt();

            if (input == 1) {
                clearConsole();
                System.out.print("SIGN IN\nEmail: ");
                String email = br.readLine();
                while (!validateEmail(email)) {
                    clearConsole();
                    System.out.print("SIGN IN\nWrong Email format!\nEmail: ");
                    email = br.readLine();
                }
                clearConsole();

                System.out.print("SIGN IN\nEmail: " + email +
                        "\nPassword: ");
                String password = br.readLine();

                user = authController.login(email, password);
                clearConsole();
                if (user == null) {
                    System.out.println("Wrong email or password!");
                    continue;
                } else {
                    System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!\n");
                    isActive = true;
                }
            } else if (input == 2) {
                clearConsole();
                System.out.print("REGISTER\nFirst name: ");
                String name = br.readLine();
                while (name.length() < 2) {
                    System.out.print("First name should be longer than 1!\nFirst name: ");
                    name = br.readLine();
                }
                clearConsole();

                System.out.print("REGISTER\nFirst name: " + name +
                        "\nSecond name: ");
                String surname = br.readLine();
                while (surname.length() < 2) {
                    System.out.print("Second name should be longer than 1!\nSecond name: ");
                    surname = br.readLine();
                }
                clearConsole();

                System.out.print("REGISTER\nFirst name: " + name +
                        "\nSecond name: " + surname +
                        "\nEmail: ");
                String email = br.readLine();
                while (!validateEmail(email)) {
                    clearConsole();
                    System.out.print("Wrong email format!\nEmail: ");
                    email = br.readLine();
                }
                clearConsole();

                System.out.print("REGISTER\nFirst name: " + name +
                        "\nSecond name: " + surname +
                        "\nEmail: " + email +
                        "\nPassword (8 characters min): ");
                String password = br.readLine();

                while (!validatePassword(password)) {
                    clearConsole();
                    System.out.println("Password must contain at least 8 symbols \n");
                    System.out.print("Password: ");

                    password = br.readLine();
                }
                clearConsole();
                System.out.println("REGISTER\nFirst name: " + name +
                        "\nSecond name: " + surname +
                        "\nEmail: " + email +
                        "\nPassword: " + password +
                        "\nYour password's strength is: "
                        + (getPasswordStrength(password) ? getStrengthBar(100) : getStrengthBar(20)) +
                        "\n\n1 - Confirm" +
                        "\n2 - Cancel");

                switch (scanner.nextInt()) {
                    default: {
                        clearConsole();
                        System.out.println("Registration has been canceled");
                        continue;
                    }
                    case 1: {
                        break;
                    }
                }

                try {
                    user = authController.register(name, surname, email, password, 1);
                } catch (UserAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                clearConsole();
                System.out.println("Cheers, mate, you've been registered\n");
                isActive = true;
            }
        }

        while (isActive) {
            int input = -1;

            System.out.println("ACCOUNT: " + user.getEmail() +
                    "\nChoose option:\n" +
                    "1 - Output product\n" +
                    "2 - Update product\n" +
                    "3 - Add product\n" +
                    "4 - Delete product\n" +
                    "5 - Close window");

            input = scanner.nextInt();

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
                        System.out.print("\n");
                        break;
                    }

                    // output product by id
                    case 2: {
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
            }

            // update product
            else if (input == 2) {
                clearConsole();
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.print("UPDATE PRODUCT\nEnter product ID: ");
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

                switch (Integer.parseInt(br.readLine())) {
                    default:
                        System.out.println("Wrong input!\n");
                        break;

                    // update name
                    case 1: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId +
                                "\nCurrent name: " + updatedProduct.getName() +
                                "\nEnter new name: ");
                        name = br.readLine();
                        while (name.length() < 2) {
                            System.out.print("UPDATE PRODUCT: " + productId
                                    + "\nName should be longer than 1!\nEnter new name: ");
                            name = br.readLine();
                        }
                        clearConsole();

                        updatedProduct.setName(name);
                        productController.updateProduct(productId, updatedProduct);
                        System.out.println("Product " + productId + " name changed to \"" + name + "\"\n");
                        break;
                    }

                    // update price
                    case 2: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId +
                                "\nCurrent price: " + updatedProduct.getPrice() +
                                "\nEnter new price: ");
                        price = scanner.nextDouble();
                        clearConsole();

                        updatedProduct.setPrice(price);
                        productController.updateProduct(productId, updatedProduct);

                        System.out.println("Product " + productId + " price changed to \"" + price + "\"\n");
                        break;
                    }

                    // update description
                    case 3: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId +
                                "\nCurrent description: " + updatedProduct.getDescription() +
                                "\nEnter new description: ");
                        description = br.readLine();
                        while (description.length() < 5) {
                            System.out.print("UPDATE PRODUCT: " + productId
                                    + "\nDescription should be longer than 5!\nEnter new description: ");
                            description = br.readLine();
                        }
                        clearConsole();

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

                System.out.print("ADD NEW PRODUCT\nName: ");
                String name = br.readLine();
                while (name.length() < 2) {
                    System.out.print("Name should be longer than 1!\nName: ");
                    name = br.readLine();
                }
                clearConsole();

                System.out.print("ADD NEW PRODUCT\nName: " + name +
                        "\nPrice: ");
                double price = scanner.nextDouble();
                clearConsole();

                System.out.print("ADD NEW PRODUCT\nName: " + name +
                        "\nPrice: " + price +
                        "\nDescription: ");
                String description = br.readLine();
                clearConsole();

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
            }

            // exit program
            else if (input == 5) {
                clearConsole();
                isActive = false;
                System.out.println("bazar CLI closed\n");
            }

            // wrong input
            else {
                clearConsole();
                System.out.println("Wrong input!\n");
            }
        }

        br.close();
        scanner.close();

    }
}