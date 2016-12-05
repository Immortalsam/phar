package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.CustomerBillInterface;
import com.phar.model.CustomerBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/29/2016.
 */
public class CustomerBillImplement implements CustomerBillInterface{

    private Connection connection;

    public CustomerBillImplement(){
        try{
            connection = DatabaseConnection.getConnection();
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean addCustomerBill(CustomerBill customerBill) {

        String query = "INSERT into customer_bill VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customerBill.getCustomerId());
            preparedStatement.setString(2, customerBill.getSalesDate());
            preparedStatement.setString(3, customerBill.getCustomerBillNo());
            preparedStatement.setDouble(4, customerBill.getTotalAmount());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
