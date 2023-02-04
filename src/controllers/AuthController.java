package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import models.Controller;

public class AuthController extends Controller {

    public AuthController() throws Exception {}

    public User register(String firstName, String lastName, String email, String password) {
        try {
            // Hash the password
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            // Create a new user
            String insertUserSQL = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertUserSQL,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setBytes(4, hashedPassword);
            preparedStatement.executeUpdate();

            // Get the generated user ID
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                return new User(userId, firstName, lastName, email, hashedPassword);
            }
        } catch (NoSuchAlgorithmException | SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
