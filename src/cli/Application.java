package cli;

import controllers.ProductController;
import controllers.UserController;

import entities.Product;
import entities.User;
import entities.Cart;

import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.io.BufferedReader;

public class Application {
    private final UserController userController;
    private final ProductController productController;

    private User currentUser;

    private Cart currentCart;

    private BufferedReader br;

    private Scanner scanner;

    public Application(UserController useController, ProductController productController) {
        this.userController = useController;
        this.productController = productController;

        br=new BufferedReader(br);
        scanner=new Scanner(System.in);

        currentCart = new Cart();
    }

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

    public void printAppLogo() {
        System.out.println(
                "\n████████████████████████████████\n" +
                        "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                        "██░░░░████░░░░██████████░░░░████\n" +
                        "██░░░░░░░░███████████░░░░███████\n" +
                        "██░░░░████░░░░█████░░░░█████████\n" +
                        "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                        "████████████████████████████████");
    }

    public void run() {
        currentUser = authMenu();
        int userRole = currentUser.getRoleId();

        if (userRole == 1) {
            adminMenu();
        } else {
            clientMenu();
        }
    }

    public User authMenu() {
        int input = -1;
        User user;
        clearConsole();
        printAppLogo();
        System.out.println("________________________________\n");
        System.out.println("Welcome to bazar!©");
        System.out.println("________________________________\n");

        while (true) {
            System.out.println("\n1 - Log in\n2 - Sign up");
            input = scanner.nextInt();

            if (input == 1) {
                clearConsole();
                System.out.print("LOGIN\nEmail: ");
                String email = scanner.next();
                while (!validateEmail(email)) {
                    clearConsole();
                    System.out.print("LOGIN\nWrong email format!\nEmail: ");
                    email = scanner.next();
                }
                clearConsole();

                System.out.print("LOGIN\nEmail: " + email + "\nPassword: ");
                String password = scanner.next();

                clearConsole();
                boolean login = userController.login(email, password);
                if (!login) {
                    clearConsole();
                    System.out.println("Wrong email or password!");
                    continue;
                }

                return userController.getUserByEmail(email);

            } else if (input == 2) {
                clearConsole();
                System.out.print("SIGNUP\nFirst name: ");
                String firstName = scanner.next();

                System.out.print("Last name: ");
                String lastName = scanner.next();

                System.out.print("Email: ");
                String email = scanner.next();
                while (!validateEmail(email)) {
                    clearConsole();
                    System.out.print("SIGNUP\nWrong email format!\nEmail: ");
                    email = scanner.next();
                }
                clearConsole();

                System.out.print("SIGNUP\nFirst name: " + firstName +
                        "\nLast name: " + lastName +
                        "\nEmail: " + email +
                        "\nPassword: ");
                String password = scanner.next();
                while (!validatePassword(password)) {
                    clearConsole();
                    System.out.print("SIGNUP\nPassword should be longer than 7!\nPassword: ");
                    password = scanner.next();
                }
                clearConsole();

                System.out.println("SIGNUP\nFirst name: " + firstName +
                        "\nLast name: " + lastName +
                        "\nEmail: " + email +
                        "\nPassword: " + password + "\n");

                if (getPasswordStrength(password)) {
                    System.out.println("Your password is: " + getStrengthBar(90) + " Hard");
                } else {
                    System.out.println("Your password is: " + getStrengthBar(30) + " Weak");
                }

                System.out.print("\nAre you sure? (y/n): ");

                switch (scanner.next()) {
                    default: {
                        clearConsole();
                        System.out.println("Sign up has been canceled\n");
                        continue;
                    }
                    case "y": {
                        clearConsole();
                        user = new User(firstName, lastName, email, 2);
                        boolean register = userController.register(user, password);

                        if (register) {
                            clearConsole();
                            System.out.println("Sign up has been completed successfully!\nRunning CLI...");
                            return user;
                        } else {
                            clearConsole();
                            user = null;
                            System.out.println("Sign up stopped due to unknown error!\n");
                            continue;
                        }
                    }
                }
            } else {
                clearConsole();
                System.out.println("Wrong input!\n");
            }
        }
    }

    public void adminMenu() {
        int input = -1;

        clearConsole();

        while (true) {
            System.out.println("ACCOUNT: " + currentUser.getEmail() +
                    "\nChoose option:\n" +
                    "0 - User controller\n" +
                    "1 - Output products\n" +
                    "2 - Update product\n" +
                    "3 - Add product\n" +
                    "4 - Delete product\n" +
                    "5 - Close window");

            input = scanner.nextInt();

            if (input == 0) {
                clearConsole();

                System.out.println("USER CONTROLLER\n" +
                        "Choose option:\n" +
                        "1 - Output users\n" +
                        "2 - Delete users\n" +
                        "3 - Set Admin\n" +
                        "4 - Back");

                switch (scanner.nextInt()) {
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
                        switch (scanner.nextInt()) {
                            default: {
                                clearConsole();
                                System.out.println("Wrong input!\n");
                                break;
                            }
                            case 1: {
                                clearConsole();
                                System.out.println(userController.getAllUsers() + "\n");
                                break;
                            }
                            case 2: {
                                clearConsole();
                                System.out.print("OUTPUT USER BY ID\nEnter user ID: ");
                                int userId = scanner.nextInt();
                                clearConsole();

                                try {
                                    System.out.println(userController.getUser(userId) + "\n");
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }

                                break;
                            }
                            case 3: {
                                clearConsole();
                                System.out.print("OUTPUT USER BY EMAIL\nEnter user email: ");
                                String email = scanner.next();

                                while (!validateEmail(email)) {
                                    clearConsole();
                                    System.out.print("Wrong input!\nOUTPUT USER BY EMAIL\nEnter user email: ");
                                    email = scanner.next();
                                }
                                clearConsole();

                                try {
                                    System.out.println(userController.getUserByEmail(email) + "\n");
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage() + "\n");
                                }

                                break;
                            }
                            case 4: {
                                clearConsole();
                                System.out.print("OUTPUT USERS BY FIRST NAME\nEnter first name: ");
                                String firstName = scanner.next();

                                while (firstName.length() < 1) {
                                    System.out.print(
                                            "Name should be longer than 1!\nOUTPUT USER BY FIRST NAME\nEnter first name: ");
                                    firstName = scanner.next();
                                }
                                clearConsole();
                                System.out.println(userController.getUsersByFirstName(firstName) + "\n");

                                break;
                            }
                            case 5: {
                                clearConsole();
                                System.out.print("OUTPUT USERS BY LAST NAME\nEnter last name: ");
                                String lastName = scanner.next();

                                while (lastName.length() < 1) {
                                    System.out.print(
                                            "Name should be longer than 1!\nOUTPUT USER BY LAST NAME\nEnter last name: ");
                                    lastName = scanner.next();
                                }
                                clearConsole();
                                System.out.println(userController.getUsersByLastName(lastName) + "\n");

                                break;
                            }
                            case 6: {
                                clearConsole();
                                System.out.println(userController.getUsersByRoleId(1) + "\n");

                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        clearConsole();
                        System.out.print("DELETE USER\nEnter user ID: ");
                        int userId = scanner.nextInt();
                        clearConsole();
                        System.out.println(userController.deleteUser(userId) + "\n");
                        break;
                    }
                    case 3: {
                        clearConsole();
                        System.out.print("SET ADMIN\nEnter user id: ");
                        int userId = scanner.nextInt();
                        clearConsole();
                        System.out.println(userController.changeUserRole(userId, 1) + "\n");
                        break;
                    }
                    case 4: {
                        clearConsole();
                    }
                }
            } else if (input == 1) {
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
                        System.out.println(productController.getAllProducts() + "\n");
                        break;
                    }

                    // output product by id
                    case 2: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY ID\nEnter product ID: ");
                        int productId = scanner.nextInt();

                        clearConsole();
                        System.out.println(productController.getProduct(productId) + "\n");
                        break;
                    }

                    case 3: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY NAME\nEnter product name: ");
                        String productName = scanner.next();

                        while (productName.length() < 2) {
                            System.out.print("Product name should be longer than 1!\nEnter product name: ");
                            productName = scanner.next();
                        }

                        clearConsole();
                        System.out.println(productController.getProductsByName(productName) + "\n");
                        break;
                    }

                    case 4: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY PRICE\nStart price: ");
                        double start = scanner.nextDouble();

                        System.out.print(start + "\nEnd price: ");
                        double end = scanner.nextDouble();

                        while (end < start) {
                            clearConsole();
                            System.out.print("End price can't be less than start price\nEnd price: ");
                            end = scanner.nextDouble();
                        }

                        clearConsole();
                        System.out.println(productController.getProductsByPrice(start, end) + "\n");
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
                Product updatedProduct;
                String name;
                double price;
                String description;
                int productId;

                System.out.print("UPDATE PRODUCT\nEnter product ID: ");
                productId = scanner.nextInt();
                clearConsole();

                updatedProduct = productController.getProduct(productId);

                while (updatedProduct == null) {
                    clearConsole();
                    System.out.print("Wrong input!\nEnter product ID: ");
                    productId = scanner.nextInt();
                    updatedProduct = productController.getProduct(productId);
                }

                System.out.println("UPDATE PRODUCT: " + productId +
                        "\nChoose option:\n" +
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
                        name = scanner.next();
                        while (name.length() < 2) {
                            System.out.print("UPDATE PRODUCT: " + productId
                                    + "\nName should be longer than 1!\nEnter new name: ");
                            name = scanner.nextLine();
                        }
                        clearConsole();

                        updatedProduct.setName(name);
                        System.out.println(productController.updateProduct(productId, updatedProduct));
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
                        System.out.println(productController.updateProduct(productId, updatedProduct));
                        break;
                    }

                    // update description
                    case 3: {
                        clearConsole();
                        System.out.print("UPDATE PRODUCT: " + productId +
                                "\nCurrent description: " + updatedProduct.getDescription() +
                                "\nEnter new description: ");
                        description = scanner.next();
                        while (description.length() < 5) {
                            System.out.print("UPDATE PRODUCT: " + productId
                                    + "\nDescription should be longer than 5!\nEnter new description: ");
                            description = scanner.next();
                        }
                        clearConsole();

                        updatedProduct.setDescription(description);
                        System.out.println(productController.updateProduct(productId, updatedProduct));
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
                String name = scanner.nextLine();

                System.out.print(name + "\nPrice: ");
                double price = scanner.nextDouble();

                System.out.print(price + "\nDescription: ");
                String description = scanner.next();

                if (name.length() < 2 && price < 0 && description.length() < 2) {
                    clearConsole();
                    System.out.println("Error occured while creating product!");
                } else {
                    clearConsole();
                    Product product = new Product(name, description, price);
                    System.out.println(productController.addProduct(product));
                }
            }
            // delete product
            else if (input == 4) {
                clearConsole();

                System.out.print("Enter product id: ");
                int productId = scanner.nextInt();
                clearConsole();

                System.out.print("DELETE PRODUCT: " + productId + "\nAre you sure? (y/n): ");
                String confirmation = scanner.next();

                switch (confirmation) {
                    default:
                        System.out.println("Wrong input!\n");
                        break;
                    case "y": {
                        clearConsole();
                        System.out.println(productController.deleteProduct(productId));
                        ;
                    }
                    case "n": {
                        clearConsole();
                        System.out.println("Product " + productId + " was NOT deleted");
                        break;
                    }
                }
            }
            // exit program
            else if (input == 5) {
                clearConsole();
                System.out.println("bazar CLI closed\n");
                return;
            }
            // wrong input
            else {
                clearConsole();
                System.out.println("Wrong input!\n");
            }
        }
    }

    public void clientMenu() {
        int input = -1;

        clearConsole();

        while (true) {
            System.out.println("ACCOUNT: " + currentUser.getEmail() +
                    "\nChoose option:\n" +
                    "1 - Output products\n" +
                    "2 - Add product to cart\n" +
                    "3 - Delete product from cart\n" +
                    "4 - Check cart\n" +
                    "5 - Make order\n" +
                    "6 - Close CLI");

            input = scanner.nextInt();

            if (input == 1) {
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
                        System.out.println(productController.getAllProducts() + "\n");
                        break;
                    }

                    // output product by id
                    case 2: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY ID\nEnter product ID: ");
                        int productId = scanner.nextInt();

                        clearConsole();
                        System.out.println(productController.getProduct(productId) + "\n");
                        break;
                    }

                    case 3: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY NAME\nEnter product name: ");
                        String productName = scanner.nextLine();

                        while (productName.length() < 2) {
                            System.out.print("Product name should be longer than 1!\nEnter product name: ");
                            productName = scanner.nextLine();
                        }

                        clearConsole();
                        System.out.println(productController.getProductsByName(productName) + "\n");
                        break;
                    }

                    case 4: {
                        clearConsole();
                        System.out.print("OUTPUT PRODUCT BY PRICE\nStart price: ");
                        double start = scanner.nextDouble();

                        System.out.print(start + "\nEnd price: ");
                        double end = scanner.nextDouble();

                        while (end < start) {
                            clearConsole();
                            System.out.print("End price can't be less than start price\nEnd price: ");
                            end = scanner.nextDouble();
                        }

                        clearConsole();
                        System.out.println(productController.getProductsByPrice(start, end) + "\n");
                        break;
                    }

                    case 5: {
                        clearConsole();
                    }
                }
            } else if (input == 2) {
                clearConsole();

                System.out.print("ADD PRODUCT TO CART\nEnter product ID: ");

                int productId = scanner.nextInt();
                Product product = productController.getProduct(productId);

                if (product != null) {
                    currentCart.addProduct(product);
                    System.out.println("Product " + productId + " added to cart\n");
                }
            }
            // delete product from cart
            else if (input == 3) {
                clearConsole();

                System.out.print("DELETE PRODUCT FROM CART\nEnter product ID: ");

                int productId = scanner.nextInt();
                Product product = productController.getProduct(productId);

                boolean existInCart = currentCart.isInCart(product);

                if (!existInCart) {
                    System.out.println("Product " + productId + " is not in cart\n");
                }

                if (product != null && existInCart) {
                    currentCart.removeProduct(product);
                    System.out.println("Product " + productId + " removed from cart\n");
                }
            }
            // check cart
            else if (input == 4) {
                clearConsole();
                System.out.println(currentCart);
                System.out.println();
            }
            // create order
            else if (input == 5) {
                clearConsole();
            } else if (input == 6) {
                clearConsole();
                System.out.println("bazar CLI closed\n");
                return;
            } else {
                clearConsole();
                System.out.println("Wrong input!");
            }
        } // end while(true) loop

    }

}
