package models;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] password;

    public User(int id, String firstName, String lastName, String email, byte[] password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public byte[] getPassword() {
        return password;
    }
}
