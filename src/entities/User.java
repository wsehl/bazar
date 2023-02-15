package entities;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int roleId;

    public User(String firstName, String lastName, String email, int roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
    }

    public User(int id, String firstName, String lastName, String email, int roleId) {
        this(firstName, lastName, email, roleId);
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", first_name = '" + firstName + '\'' +
                ", second_name = '" + lastName + '\'' +
                ", email = '" + email + '\'' +
                ", role = '" + ((roleId == 1) ? "Admin" : "User") + "'}";
    }

}
