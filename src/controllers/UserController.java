package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IUserController;
import exceptions.ObjectNotFoundException;
import models.User;
import models.Controller;

public class UserController extends Controller implements IUserController {

    public UserController() throws Exception {
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                users.add(new User(userId, userFirstName, userSecondName, userEmail, userRoleId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int id) throws Exception {
        User user;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE user_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);
            } else {
                throw new ObjectNotFoundException("User " + id + " wasn't found");
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersByFirstName(String name) throws Exception {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE first_name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                users.add(new User(userId, userFirstName, userSecondName, userEmail, userRoleId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getUsersBySecondName(String name) {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE last_name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                users.add(new User(userId, userFirstName, userSecondName, userEmail, userRoleId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getUsersByRole(int id) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE role_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                users.add(new User(userId, userFirstName, userSecondName, userEmail, userRoleId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserByEmail(String email) throws Exception {
        User user;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);
            } else {
                throw new ObjectNotFoundException("User with email " + email + " wasn't found");
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAdminRole(int UserId) throws Exception {
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM users where user_id = ?");
            statement.setInt(1, UserId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ObjectNotFoundException("User with id " + UserId + " wasn't found");
            }

            statement = getConnection().prepareStatement("UPDATE users SET role_id = ? where user_id = ?");
            statement.setInt(1, 1);
            statement.setInt(2, UserId);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        try {
            PreparedStatement statement = getConnection().prepareStatement("DELETE FROM users WHERE user_id = ?");
            statement.setInt(1, userId);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
