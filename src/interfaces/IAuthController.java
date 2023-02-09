package interfaces;

import models.User;

public interface IAuthController {
    public User register(String firstName, String lastName, String email, String password, int roleId) throws Exception;
    public User login(String email, String password);
}
