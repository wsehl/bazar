import java.sql.Connection;
import java.sql.Statement;

import db.DBConnector;

public class App {
    public static void main(String[] args) throws Exception {
        Connection connection = new DBConnector().getConnection();

        try {
            Statement stmt = connection.createStatement();
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
