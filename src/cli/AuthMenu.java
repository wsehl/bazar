package cli;

import java.util.Scanner;

import controllers.UserController;
import entities.User;
import repositories.UserRepository;

public class AuthMenu extends Menu {

    private UserController userController;
    private UserRepository userRepository;
    private User user;

    public AuthMenu(UserRepository userRepository, UserController userController) {
        this.userController = userController;
        this.userRepository = userRepository;
    }

    public User run() {
        int input = -1;
        Scanner scanner = new Scanner(System.in);

        clearConsole();
        outputLogo();
        System.out.println("________________________________\n");
        System.out.println("Welcome to bazar!©");
        System.out.println("________________________________\n");

        while(true) {

            System.out.println("\n1 - Log in\n2 - Sign up");
            input = scanner.nextInt();

            if (input == 1) {
                clearConsole();
                System.out.print("LOGIN\nEmail: ");
                String email = scanner.next();

                while(!validateEmail(email)) {
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
                
                scanner.close();
                return userRepository.getUserByEmail(email);

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

                if(getPasswordStrength(password)) {
                    System.out.println("Your password is: " + getStrengthBar(90) + " Hard");
                } else {
                    System.out.println("Your password is: " + getStrengthBar(30) + " Weak");
                }

                System.out.print("\nAre you sure? (y/n): ");

                switch(scanner.next()) {
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

}
