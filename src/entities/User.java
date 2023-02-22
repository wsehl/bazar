package entities;

public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected int roleId;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(int id, String firstName, String lastName, String email) {
        this(firstName, lastName, email);
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

    @Override
    public String toString() {
        return "User {" +
                "id = " + id +
                ", first_name = '" + firstName + '\'' +
                ", second_name = '" + lastName + '\'' +
                ", email = '" + email + "'}";
    }

}
