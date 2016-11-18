package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.InventoryService;
import com.phar.model.Inventory;
import com.phar.model.ProductEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by somecuitears on 11/18/16.
 */
public class InventoryImplement implements InventoryService {
    private Connection connection;

    @Override

    public void addtoDb(ProductEntry product) {
        Inventory inventory = new Inventory();
        inventory.setProductName(product.getProductName());
        inventory.setProductID(product.getProductId());
        inventory.setQuantity((double) product.getProductQuantity());
        inventory.setBatch(String.valueOf(product.getProductBatch()));
        inventory.setmRP(((double) product.getProductMrp()));
        inventory.setExpireDate(product.getProductExpDate());
        String insertToInventory = "INSERT INTO inventory VALUES(?,?,?,?,?,?,?)";

        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertToInventory);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, inventory.getProductID());
            preparedStatement.setString(3, inventory.getProductName());
            preparedStatement.setDouble(4, inventory.getQuantity());
            preparedStatement.setString(5, inventory.getBatch());
            preparedStatement.setDouble(6, inventory.getmRP());
            preparedStatement.setString(7, inventory.getExpireDate());
            preparedStatement.executeUpdate();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
