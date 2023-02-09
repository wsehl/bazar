package controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.UserAlreadyExistsException;
import models.User;
import models.Controller;
import utils.PBKDF2;

public class AuthController extends Controller {

    public AuthController() throws Exception {
    }

    private final byte[] SALT = "salt".getBytes();

    public User register(String firstName, String lastName, String email, String password, int roleId) throws Exception {
        try {
            String getUserSQL = "SELECT * FROM users WHERE email = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(getUserSQL);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("email").equals(email)) {
                    throw new UserAlreadyExistsException("User with this email already exists!");
                }
            }

            String hashedPassword = PBKDF2.bytesToHex(PBKDF2.getEncryptedPassword(password, SALT));

            String insertUserSQL = "INSERT INTO users (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(insertUserSQL,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setInt(5, roleId);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                return new User(userId, firstName, lastName, email, roleId);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public User login(String email, String password) {
        try {
            String hashedPassword = PBKDF2.bytesToHex(PBKDF2.getEncryptedPassword(password, SALT));

            String getUserSQL = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(getUserSQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int roleId = resultSet.getInt("role_id");
                return new User(userId, firstName, lastName, email, roleId);
            }

        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
