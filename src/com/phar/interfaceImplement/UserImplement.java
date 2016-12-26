package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.UserInterface;
import com.phar.model.SalesAdmin;
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
        String query = "SELECT * from user";
        try (Statement stat = conn.createStatement()) {
            ResultSet res = stat.executeQuery(query);
            while (res.next()) {
                user.setUsername(res.getString("user_name"));
                user.setPassword(res.getString("pass_word"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkSalesAdmin(SalesAdmin salesAdmin) {
        String query = "SELECT * from sales_admin";
        try (Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);

            while (resultSet.next()) {
                salesAdmin.setUsername(resultSet.getString("sales_username"));
                salesAdmin.setPassword(resultSet.getString("sales_password"));
                return true;
            }
            resultSet.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
