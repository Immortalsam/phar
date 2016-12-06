package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.BillInterface;
import com.phar.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/23/2016.
 */
public class BillInterfaceImplement implements BillInterface {

    private Bill bill;
    private PreparedStatement preparedStatement;
    private Connection connection;

    public BillInterfaceImplement() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addBill(Bill bill) {
        this.bill = bill;
        String query = "INSERT into bill (purchase_date, bill_no, supplier_id, total_amount) values (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, bill.getPurchaseDate());
            preparedStatement.setString(2, bill.getBillNo());
            preparedStatement.setString(3, bill.getSupplierId());
            preparedStatement.setFloat(4, bill.getTotalAmount());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
