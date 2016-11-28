package com.phar.interfaceImplement;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.interfaces.SupplierServices;
import com.phar.model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by Sam on 11/6/2016.
 */
public class SupplierImplement implements SupplierServices {

    static Connection conn;

    public SupplierImplement() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean addSupplier(Supplier supplier) {

        if (supplier.getPanNo().length() != 10) {
            CustomAlert alert = new CustomAlert("PAN Length Invalid", "PAN no should be minimum of 10 length");
            alert.withoutHeader();
        } else {

            String addquery = "INSERT into supplier (supplier_id, supplier_name, supplier_address, supplier_contact, " +
                    "supplier_email, pan_no) VALUES (?,?,?,?,?,?)";

            try {
                PreparedStatement stat = conn.prepareStatement(addquery);
                stat.setString(1, supplier.getSupplierId());
                stat.setString(2, supplier.getSupplierName());
                stat.setString(3, supplier.getSupplierAddress());
                stat.setString(4, supplier.getSupplierContact());
                stat.setString(5, supplier.getSupplierEmail());
                stat.setString(6, supplier.getPanNo());
                stat.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean editSupplier() {
        return false;
    }

    @Override
    public boolean deleteSupplier() {
        return false;
    }

    @Override
    public ObservableList<Supplier> listSupplier() {


        ObservableList<Supplier> supplierData = FXCollections.observableArrayList();
        String query = "SELECT * from supplier";

        try (Statement stat = conn.createStatement()) {
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(res.getString("supplier_id"));
                supplier.setSupplierName(res.getString("supplier_name"));
                supplier.setSupplierAddress(res.getString("supplier_address"));
                supplier.setSupplierContact(res.getString("supplier_contact"));
                supplier.setSupplierEmail(res.getString("supplier_email"));
                supplier.setPanNo(res.getString("pan_no"));

                supplierData.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierData;
    }
}
