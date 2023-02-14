package repositories;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.IDB;
import entities.User;
import exceptions.NoDatabaseConnectionException;
import exceptions.UserAlreadyExistsException;
import repositories.interfaces.IUserRepository;
import utils.PBKDF2;

public class UserRepository implements IUserRepository {
    private final IDB db;
    private final byte[] SALT = "salt".getBytes();

    public UserRepository(IDB db) {
        this.db = db;
    }

    public List<User> getUsers() {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");

                User user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);

                users.add(user);

                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public List<User> getUsersByFirstName(String firstName) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM users WHERE first_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, firstName);

            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");
                
                User user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);

                users.add(user);

                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public User getUser(int id) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");

                User user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public User getUserByEmail(String email) {
        Connection connection = null;

        try {

            connection = db.getConnection();

            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userFirstName = resultSet.getString("first_name");
                String userSecondName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                int userRoleId = resultSet.getInt("role_id");

                User user = new User(userId, userFirstName, userSecondName, userEmail, userRoleId);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return null;
    }

    public boolean authenticateUser(String email, String password) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            String hashedPassword = PBKDF2.bytesToHex(PBKDF2.encryptPassword(password, SALT));

            statement.setString(1, email);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public int createUser(User user, String password) throws UserAlreadyExistsException {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String findUserQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement userStatement = connection.prepareStatement(findUserQuery);

            userStatement.setString(1, user.getEmail());

            ResultSet userResultSet = userStatement.executeQuery();
            if (userResultSet.next()) {
                if (userResultSet.getString("email").equals(user.getEmail())) {
                    throw new UserAlreadyExistsException("User with this email already exists!");
                }
            }

            String query = "INSERT INTO users (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?) RETURNING user_id";
            PreparedStatement statement = connection.prepareStatement(query);

            String hashedPassword = PBKDF2.bytesToHex(PBKDF2.encryptPassword(password, SALT));

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setInt(5, user.getRoleId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return -1;
    }

    public boolean deleteUser(int id) {
        Connection connection = null;

        try {
            connection = db.getConnection();

            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            statement.executeQuery();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoDatabaseConnectionException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }
}
