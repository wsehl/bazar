package entities;

public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected int roleId;

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
    //public abstract void (should be, but setId(*not empty*))
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

    public class Client extends User {
        public Client(String firstName, String lastName, String email, int roleId){
            super(firstName, lastName, email, roleId);
        }

        @Override

        public void setId(int id){
            super.setId(id);
        }
    }
    
    public class Admin extends User {
        public Admin(String firstName, String lastName, String email, int roleId){
            super(firstName, lastName, email, roleId);
        }

        @Override

        public void setId(int id){
            super.setId(id);
        }
    }
}
