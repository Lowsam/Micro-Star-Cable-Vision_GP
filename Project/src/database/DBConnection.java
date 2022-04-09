package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static String url = "jdbc:mysql://localhost:3307/CustomerDb";
    private static Connection myConn;

    public static Connection getDatabaseConnection() throws SQLException {
        if (myConn == null) {
            myConn = DriverManager.getConnection(url, "root", "password");
        }
        return myConn;
    }
}

