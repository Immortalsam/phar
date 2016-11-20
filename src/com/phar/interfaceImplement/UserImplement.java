package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.UserInterface;
import com.phar.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Sam on 11/10/2016.
 */
public class UserImplement implements UserInterface {

    static Connection conn;
    private User user;

    public UserImplement() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean checkUser(User user) {
        String query = "SELECT * from user ";
        try (Statement stat = conn.createStatement()) {
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                user.setUsername(res.getString("user_name"));
                user.setPassword(res.getString("pass_word"));
                return true;
            }
            res.close();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
