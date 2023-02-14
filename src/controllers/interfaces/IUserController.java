package controllers.interfaces;

import entities.User;

public interface IUserController {
    String getAllUsers();

    String getUserByEmail(String email);

    String getUsersByFirstName(String firstName);

    String getUsersByLastName(String lastName);

    String getUser(int id);

    boolean login(String email, String password);

    boolean register(User user, String password);

    String deleteUser(int id);
}
