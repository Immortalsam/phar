package com.phar.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Sam on 11/6/2016.
 */

public class DatabaseConnection {

    private static Connection conn;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy", "root", "");
        return conn;
    }
}