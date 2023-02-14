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

    public String getUsersByFirstName(String firstName) {
        List<User> users = userRepository.getUsersByFirstName(firstName);

        if (users.size() == 0) {
            return "No users with first name \'" + firstName + "\' found";
        }

        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append(user.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getUsersByLastName(String lastName) {
        List<User> users = userRepository.getUsersByLastName(lastName);

        if (users.size() == 0) {
            return "No users with first name \'" + lastName + "\' found";
        }

        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append(user.toString()).append("\n");
        }

        return sb.toString();
    }

    public String getUsersByRoleId(int roleId) {
        List<User> users = userRepository.getUsersByRoleId(roleId);

        if (users.size() == 0) {
            return "No users with role id \'" + roleId + "\' found";
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

    public String getUserByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");

        User foundUser = userRepository.getUserByEmail(email);

        if (foundUser == null)
            return "User not found";

        return foundUser.toString();
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

    public String changeUserRole(int userId, int newRoleId) {
        if (userId < 0 || newRoleId < 0)
            return "Id cannot be negative";

        boolean changed = userRepository.changeUserRole(userId, newRoleId);

        if (changed)
            return "User's role updated successfully";

        return "User not found";
    }

}
