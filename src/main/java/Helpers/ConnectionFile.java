package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFile {

    public static String DBusername = "admin";
    public static String DBpassword = "1122";
    public static String dbURL = "jdbc:sqlserver://SS-Pavillion\\SQLEXPRESS;databaseName=PharmacyDB;trustServerCertificate=true";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, DBusername, DBpassword);
    }
}
