package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by somecuitears on 11/15/16.
 */
public class productEntryController implements Initializable {

    Connection connection;
    CheckComboBox genericComposition;
    private final ObservableList<String> strings = FXCollections.observableArrayList();

    @FXML
    private GridPane genericCompGrid;
    @FXML
    private ComboBox productGroup, productVat, productCategory;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        try {
            connection = DatabaseConnection.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
