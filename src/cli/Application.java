package cli;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.ProductController;
import controllers.UserController;
import entities.User;

public class Application {
    private final Scanner scanner;
    private final UserController userController;
    private final ProductController productController;

    private User currentUser;

    public Application(UserController controller, ProductController productController) {
        this.userController = controller;
        this.productController = productController;

        scanner = new Scanner(System.in);
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void getLoginMenu() {
        System.out.println("Enter email:");
        String email = scanner.next();

        System.out.println("Enter password:");
        String password = scanner.next();

        boolean result = userController.login(email, password);

        if (result) {
            System.out.println("Login successful");
            currentUser = userController.getUserByEmail(email);
        } else {
            System.out.println("Login failed");
        }
    }

    public void getRegisterMenu() {
        try {
            System.out.println("Enter name:");
            String name = scanner.next();

            System.out.println("Enter surname:");
            String surname = scanner.next();

            System.out.println("Enter email:");
            String email = scanner.next();

            System.out.println("Enter password:");
            String password = scanner.next();

            boolean result = userController.register(new User(name, surname, email, 1), password);

            if (result) {
                System.out.println("Registration successful");
                currentUser = userController.getUserByEmail(email);
            } else {
                System.out.println("Registration failed");
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void run() {
        System.out.println();
        System.out.println(
                "████████████████████████████████\n" +
                        "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                        "██░░░░████░░░░██████████░░░░████\n" +
                        "██░░░░░░░░███████████░░░░███████\n" +
                        "██░░░░████░░░░█████░░░░█████████\n" +
                        "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                        "████████████████████████████████");
        System.out.println("________________________________");
        System.out.println();
        System.out.println("Welcome to bazar!©");
        System.out.println("________________________________");
        System.out.println();

        while (true) {
            if (currentUser == null) {
                try {
                    System.out.println("Choose option:\n" +
                            "1 - Sign In\n" +
                            "2 - Sign Up");

                    int option = scanner.nextInt();

                    if (option == 1) {
                        getLoginMenu();
                    } else if (option == 2) {
                        getRegisterMenu();
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input must be integer");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    System.out.println("ACCOUNT: " + currentUser.getEmail() +
                            "\nChoose option:\n" + ((currentUser.getRoleId() == 1) ? "0 - User controller\n" : "") +
                            "1 - Output products\n" +
                            "2 - Update product\n" +
                            "3 - Add product\n" +
                            "4 - Delete product\n" +
                            "5 - Close window");

                    int option = scanner.nextInt();

                    if (option == 1) {
                    } else if (option == 2) {
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input must be integer");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

}
