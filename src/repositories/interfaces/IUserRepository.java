package repositories.interfaces;

import java.util.List;

import entities.User;
import exceptions.UserAlreadyExistsException;

public interface IUserRepository {
    List<User> getUsers();

    User getUser(int id);

    User getUserByEmail(String email);

    boolean authenticateUser(String email, String password);

    int createUser(User user, String password) throws UserAlreadyExistsException;

    boolean deleteUser(int id);
}
