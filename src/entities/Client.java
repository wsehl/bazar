package entities;

public class Client extends User {

    public Client(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public Client(int id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Client {" +
                "id = " + id +
                ", first_name = '" + firstName + '\'' +
                ", second_name = '" + lastName + '\'' +
                ", email = '" + email + "'}";
    }

}