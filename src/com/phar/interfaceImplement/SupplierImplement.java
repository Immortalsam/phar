package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.SupplierServices;
import com.phar.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

        String addquery = "INSERT into supplier (supplier_id, supplier_name, supplier_address, supplier_contact, " +
                "supplier_category, pan_no) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement stat = conn.prepareStatement(addquery);
            stat.setString(1, supplier.getSupplierId());
            stat.setString(2, supplier.getSupplierName());
            stat.setString(3, supplier.getSupplierAddress());
            stat.setString(4, supplier.getSupplierContact());
            stat.setString(5, supplier.getSupplierCategory());
            stat.setDouble(6, supplier.getPanNo());
            stat.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
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
    public List<String> listSupplier() {
        return null;
    }
}
