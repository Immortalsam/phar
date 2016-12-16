package com.phar.database;

import com.phar.custom.CustomAlert;

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
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy", "root", "");
        } catch (Exception e) {
//            System.out.println("Server not Found");
            CustomAlert customAlert = new CustomAlert("Server Error", "Cannot Establish Connection to Server ");
            customAlert.withoutHeader();
        }
        return conn;
    }
}