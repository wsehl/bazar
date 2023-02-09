import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.AuthController;
import controllers.ProductController;
import controllers.UserController;
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
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);
        User user = null;
        boolean isActive = false;
        clearConsole();
        System.out.println("████████████████████████████████\n" +
                "████████████████████████████████\n" +
                "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                "██░░░░████░░░░██████████░░░░████\n" +
                "██░░░░░░░░███████████░░░░███████\n" +
                "██░░░░████░░░░█████░░░░█████████\n" +
                "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
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

                System.out.print("Password: ");
                String password = br.readLine();

                user = authController.login(email, password);
                clearConsole();
                if (user == null) {
                    System.out.println("Wrong email or password!");
                    continue;
                } else {
                    System.out.println("Welcome to bazar, " + user.getFirstName() + " " + user.getLastName() + "!\n");
                    isActive = true;
                }
            } else if (input == 2) {
                clearConsole();
                System.out.print("REGISTER\nFirst name: ");
                String name = br.readLine();
                while (name.length() < 2) {
                    System.out.print("REGISTER\nFirst name should be longer than 1!\nFirst name: ");
                    name = br.readLine();
                }

                System.out.print("Second name: ");
                String surname = br.readLine();
                while (surname.length() < 2) {
                    System.out.print("REGISTER\nSecond name should be longer than 1!\nSecond name: ");
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
                    System.out.print("Password must contain at least 8 symbols\nPassword: ");

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
                    user = authController.register(name, surname, email, password, 2);
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
                    "\nChoose option:\n" + ((user.getRoleId() == 1) ? "0 - User controller\n" : "") +
                    "1 - Output products\n" +
                    "2 - Update product\n" +
                    "3 - Add product\n" +
                    "4 - Delete product\n" +
                    "5 - Close window");

            input = scanner.nextInt();

            // output product
            if (input == 0 && user.getRoleId() == 1) {
                clearConsole();

                System.out.println("USER CONTROLLER\n" + 
                        "Choose option:\n" +
                        "1 - Output users\n" +
                        "2 - Delete users\n" +
                        "3 - Set Admin\n" +
                        "4 - Back");
                
                switch(scanner.nextInt()) {
                    default: {
                        clearConsole();
                        System.out.println("Wrong input!");
                        break;
                    }
                    case 1: {
                        clearConsole();
                        System.out.println("USER CONTROLLER\n" + 
                                "Choose option:\n" +
                                "1 - Output all users\n" +
                                "2 - Output user by ID\n" +
                                "3 - Output user by email\n" +
                                "4 - Output users by first name\n" +
                                "5 - Output users by second name\n" +
                                "6 - Output admin users");
                        switch(scanner.nextInt()) {
                            default: {
                                clearConsole(); 
                                System.out.println("Wrong input!\n"); 
                                break;
                            }
                            case 1: {
                                clearConsole();
                                List<User> users = userController.getUsers();
                                users.forEach(System.out::println);
                                System.out.println('\n');
                                break;
                            }
                            case 2: {
                                clearConsole();
                                System.out.print("OUTPUT USER BY ID\nEnter user ID: ");
                                int userId = scanner.nextInt();
                                try {
                                    clearConsole();
                                    System.out.println(userController.getUserById(userId) + "\n");
                                } catch (ObjectNotFoundException e) {
                                    System.out.println(e.getMessage() + "\n");
                                }
                                break;
                            }
                            case 3: {
                                clearConsole();
                                System.out.print("OUTPUT USER BY EMAIL\nEnter user email: ");
                                String email = br.readLine();
                                while (!validateEmail(email)) {
                                    clearConsole();
                                    System.out.print("Wrong input!\nOUTPUT USER BY EMAIL\nEnter user email: ");
                                    email = br.readLine();
                                }
                                clearConsole();

                                try {
                                    System.out.println(userController.getUserByEmail(email) + "\n");
                                } catch (ObjectNotFoundException e) {
                                    System.out.println(e.getMessage() + "\n");
                                }
                                break;
                            }
                            case 4: {
                                clearConsole();
                                System.out.print("OUTPUT USERS BY FIRST NAME\nEnter first name: ");
                                String firstName = br.readLine();
                                List<User> users;
                                clearConsole();
                                try {
                                    users = userController.getUsersByFirstName(firstName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                if (users.size() < 1) {
                                    System.out.println("Users with first name \"" + firstName + "\" wasn't found");
                                } else {
                                    users.forEach(System.out::println);
                                    System.out.println("\n");
                                }
                                
                                break;
                            }
                            case 5: {
                                clearConsole();
                                System.out.print("OUTPUT USERS BY SECOND NAME\nEnter second name: ");
                                String secondName = br.readLine();
                                List<User> users;
                                clearConsole();
                                try {
                                    users = userController.getUsersByFirstName(secondName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                if (users.size() < 1) {
                                    System.out.println("Users with second name \"" + secondName + "\" wasn't found\n");
                                } else {
                                    users.forEach(System.out::println);
                                    System.out.println("\n");
                                }
                                
                                break;
                            }
                            case 6: {
                                clearConsole();
                                List<User> users;
                                try {
                                    users = userController.getUsersByRole(1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                if (users.size() < 1) {
                                    System.out.println("Admin users wasn't found\n");
                                } else if (users.size() == 1) {
                                    System.out.println("You are the only admin!\n");
                                } else {
                                    users.forEach(System.out::println);
                                    System.out.println("\n");
                                }
                                
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        clearConsole();
                        System.out.print("DELETE USER\nEnter user ID: ");
                        int userId = scanner.nextInt();
                        userController.deleteUser(userId);
                        clearConsole();
                        System.out.println("User " + userId + " was deleted successfully!\n");
                        break;
                    }
                    case 3: {
                        clearConsole();
                        System.out.print("SET ADMIN\nEnter user id: ");
                        int userId = scanner.nextInt();
                        clearConsole();
                        try {
                            userController.setAdminRole(userId);
                        } catch (ObjectNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        clearConsole();
                        System.out.println("User " + userId + " become an Admin!\n");
                        break;
                    }
                    case 4: {
                        clearConsole();
                    }
                }
            }
            else if (input == 1) {
                clearConsole();

                System.out.println("Choose option:\n" +
                        "1 - Output all products\n" +
                        "2 - Output product by ID\n" +
                        "3 - Output products by name\n" +
                        "4 - Output products by price\n" +
                        "5 - Back");

                switch (scanner.nextInt()) {
                    default:
                        System.out.println("Wrong input!\n");
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
                        System.out.print("OUTPUT PRODUCT BY ID\nEnter product ID: ");
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
                        System.out.print("OUTPUT PRODUCT BY NAME\nEnter product name: ");
                        String productName = br.readLine();
                        while (productName.length() < 2) {
                            System.out.print("Product name should be longer than 1!\nEnter product name: ");
                            productName = br.readLine();
                        }

                        List<Product> products = productController.getProductsByName(productName);
                        if (products.size() < 1) {
                            System.out.println("Products with name = \"" + productName + "\" wasn't found\n");
                            break;
                        }
                        clearConsole();
                        products.forEach(System.out::println);
                        System.out.print("\n");
                        break;
                    }

                    case 4: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY PRICE\nEnter start price: ");
                        double start = scanner.nextDouble();
                        System.out.print(start + "\nEnter end price: ");
                        double end = scanner.nextDouble();
                        while (end < start) {
                            clearConsole();
                            System.out.print("End price can't be less than start price\nEnter end price: ");
                            end = scanner.nextDouble();
                        }

                        List<Product> products = productController.getProductsByPrice(start, end);
                        if (products.size() < 1) {
                            System.out.println("Products with price between \"" + start + "\" and \"" + end + " wasn't found\n");
                            break;
                        }
                        clearConsole();
                        products.forEach(System.out::println);
                        System.out.print("\n");
                        break;
                    }

                    case 5: {
                        clearConsole();
                    }
                }
            }

            // update product
            else if (input == 2) {
                clearConsole();
                if (!(user.getRoleId() == 1)) {
                    System.out.println("Access denied!\n");
                    continue;
                }
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

                switch (scanner.nextInt()) {
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
                if (!(user.getRoleId() == 1)) {
                    System.out.println("Access denied!\n");
                    continue;
                }

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
                if (!(user.getRoleId() == 1)) {
                    System.out.println("Access denied!\n");
                    continue;
                }

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