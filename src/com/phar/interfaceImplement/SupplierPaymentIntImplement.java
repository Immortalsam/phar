package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.SupplierPaymentInterface;
import com.phar.model.SupplierPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/23/2016.
 */
public class SupplierPaymentIntImplement implements SupplierPaymentInterface {

    private Connection connection;
    private SupplierPayment supplierPayment;
    private PreparedStatement preparedStatement;

    public SupplierPaymentIntImplement() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addSupplierPayment(SupplierPayment supplierPayment) {
        this.supplierPayment = supplierPayment;
        String query = "INSERT into supplier_payment(payment_date, supplier_id,amt_paid) values (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierPayment.getPaymentDate());
            preparedStatement.setString(2, supplierPayment.getSupplierId());
            preparedStatement.setFloat(3, supplierPayment.getAmtPaid());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
