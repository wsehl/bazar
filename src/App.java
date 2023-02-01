import java.sql.*;

public class App {
    static final String DB_URL = "jdbc:postgresql://195.49.215.164/marketplace";
    static final String USER = "marketplace";
    static final String PASSWORD = "marketplace";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                Statement stmt = conn.createStatement();) {
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
