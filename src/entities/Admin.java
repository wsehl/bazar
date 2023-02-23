package entities;

public class Admin extends User {

    public Admin(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public Admin(int id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Admin {" +
                "id = " + id +
                ", first_name = '" + firstName + '\'' +
                ", second_name = '" + lastName + '\'' +
                ", email = '" + email + "'}";
    }

}
