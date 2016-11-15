package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/15/2016.
 */
public class ProductEntryController implements Initializable {

    Connection connection;
    CheckComboBox genericComposition;
    private final ObservableList<String> strings = FXCollections.observableArrayList();
    String supplierIDValueNow;
    Integer supplierIDIncremnet;
    @FXML
    private GridPane genericCompGrid;
    @FXML
    private ComboBox productGroup, productVat, productCategory;

    @FXML
    private TextField productIdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productIdd.setEditable(false);
        for (int i = 1; i <= 5; i++) {
            strings.add("Composition No " + i);
        }
        genericComposition = new CheckComboBox<String>(strings);
        productCategory.getItems().addAll(Constants.productCategoryList);
        productCategory.setValue(Constants.productCategoryList[0]);
        productVat.getItems().addAll(Constants.yesNo);
        productVat.setValue(Constants.yesNo[1]);
        genericComposition = new CheckComboBox<String>(strings);
        genericCompGrid.add(genericComposition, 0, 0);
        selectDatabase();
        supplierIDIncremnet = Integer.valueOf(supplierIDValueNow.substring(3, supplierIDValueNow.length()));
        System.out.println(supplierIDIncremnet);
        supplierIDIncremnet++;
        String newQuery = Constants.supplierIDUpdateQuery + "SID" + supplierIDIncremnet + Constants.getSupplierIDUpdateQueryBack;
        System.out.println(newQuery);
        selectDatabase();
        productIdd.setText(supplierIDValueNow);
    }

    private void selectDatabase() {
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.supplierIDGenQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierIDValueNow = resultSet.getString(1);
                System.out.println(supplierIDValueNow);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComposition(DragEvent dragEvent) {
    }
}