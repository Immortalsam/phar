package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.CustomerInterface;
import com.phar.model.CustomerInfo;
import com.phar.model.CustomerPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/27/2016.
 */
public class CustomerInterfaceImplement implements CustomerInterface {

    private Connection connection;

    public CustomerInterfaceImplement() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    @Override
    public boolean addCustomer(CustomerInfo customerInfo) {
        String addquery = "INSERT into customer_info (customer_id, customer_name, customer_address, customer_contact, customer_email) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(addquery);
            ps.setString(1, customerInfo.getCustomerId());
            ps.setString(2, customerInfo.getCustomerName());
            ps.setString(3, customerInfo.getCustomerAddress());
            ps.setString(4, customerInfo.getCustomerContact());
            ps.setString(5, customerInfo.getCustomerEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addCustomerPayment(CustomerPayment customerPayment) {
        String query = "INSERT into customer_payment(payment_date,customer_id,amount_paid) VALUES (?,?,?)";
        try {
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setString(1, customerPayment.getPaymentDate());
            ps1.setString(2, customerPayment.getCustomerId());
            ps1.setFloat(3, customerPayment.getAmtPaid());
            ps1.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
