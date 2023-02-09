package interfaces;

import java.util.List;
import models.User;

public interface IUserController {
    public List<User> getUsers();
    public User getUserById(int id) throws Exception;
    public List<User> getUsersByFirstName(String name) throws Exception;
    public List<User> getUsersBySecondName(String name);
    public List<User> getUsersByRole(int id);
    public User getUserByEmail(String email) throws Exception;
    public void setAdminRole(int UserId) throws Exception;
}
