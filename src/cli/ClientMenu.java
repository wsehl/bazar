package cli;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.ProductController;
import entities.User;

public class ClientMenu {
    private ProductController productController;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_STRENGTH_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\S]{8,10}$");

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

    public ClientMenu(ProductController productController) {
        this.productController = productController;
    }

    public void run(User currentUser) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;

        clearConsole();

        while(true) {
            
            System.out.println("ACCOUNT: " + currentUser.getEmail() +
                    "\nChoose option:\n" + 
                    "1 - Output products\n" +
                    "2 - Add product to cart\n" +
                    "3 - Delete product from cart\n" +
                    "4 - Check cart\n" +
                    "5 - Make order\n" +
                    "6 - Close CLI" );

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
            }
            else if (input == 2) {
                clearConsole();
                // add product to cart
            }
            else if (input == 3) {
                clearConsole();
                // delete product from cart
            }
            else if (input == 4) {
                clearConsole();
                // check cart
            }
            else if (input == 5) {
                clearConsole();
                // create order
            }
            else if (input == 6) {
                clearConsole();
                System.out.println("bazar CLI closed\n");
                scanner.close();
                return;
            }
            else {
                clearConsole();
                System.out.println("Wrong input!");
            }
        } // end while(true) loop

    } // end run() method
    
} // end class()
