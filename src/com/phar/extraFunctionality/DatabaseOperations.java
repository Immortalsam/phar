package com.phar.extraFunctionality;

import com.phar.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by somecuitears on 11/23/16.
 */
public class DatabaseOperations {

    //    private Connection connection;
//    private PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static String query;

    public static ResultSet simpleSelect(String table, String columns, String where) {
        if (where.equals("null")) {
            if (columns.equals("*")) {
                query = "SELECT * FROM " + table;
                System.out.println(query);
            } else {
                query = "SELECT " + columns + " FROM " + table;
                System.out.println(query);
            }
        } else {
            if (columns.equals("*")) {
                query = "SELECT * FROM " + table + " WHERE " + where;
                System.out.println(query);
            } else {
                query = "SELECT " + columns + " FROM " + table + " WHERE " + where;
                System.out.println(query);
            }
        }
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
