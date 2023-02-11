package controllers;

import java.util.List;

import controllers.interfaces.IUserController;
import entities.User;
import exceptions.UserAlreadyExistsException;
import repositories.UserRepository;

public class UserController implements IUserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getAllUsers() {
        List<User> users = userRepository.getUsers();

        if (users.size() == 0) {
            return "No users found";
        }

        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append(user.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getUser(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        User foundUser = userRepository.getUser(id);

        if (foundUser == null)
            return "User not found";

        return foundUser.toString();
    }

    public User getUserByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");

        User foundUser = userRepository.getUserByEmail(email);

        if (foundUser == null)
            return null;

        return foundUser;
    }

    public boolean login(String email, String password) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty");

        boolean passedAuthentication = userRepository.authenticateUser(email, password);

        return passedAuthentication;
    }

    public boolean register(User user, String password) {
        try {
            if (user == null)
                throw new IllegalArgumentException("User cannot be null");

            if (password == null || password.isEmpty())
                throw new IllegalArgumentException("Password cannot be null or empty");

            int id = userRepository.createUser(user, password);

            if (id == -1)
                return false;

            return true;

        } catch (UserAlreadyExistsException e) {
            e.getMessage();
            return false;
        }
    }

    public String deleteUser(int id) {
        if (id < 0)
            return "Id cannot be negative";

        boolean deleted = userRepository.deleteUser(id);

        if (deleted)
            return "User deleted successfully";

        return "User not found";
    }
}
