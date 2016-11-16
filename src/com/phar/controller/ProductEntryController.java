package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.Constants;
import com.phar.extraFunctionality.CustomComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/15/2016.
 */
public class ProductEntryController implements Initializable {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private CheckComboBox<String> newGenericComposition;
    private final ObservableList<String> compositionDropDownList = FXCollections.observableArrayList();
    private String supplierIDValueNow, productIDValueNow;
    private Integer supplierIDIncrement, productIDIncrement;

    private List<String> forNewCompanyNameList = new ArrayList<String>();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label newDisplayComposition;
    @FXML
    private GridPane newGenericCompGrid;
    @FXML
    private ComboBox newProductGroup;
    @FXML
    private ComboBox<String> newCompanyName, newProductVat, newProductCategory;
    @FXML
    private TextField newProductId, newProductName, newNumberOfPackPerUnit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //to generate product group list
        new CustomComboBox<>(newProductGroup);
        //to generate company name list
        new CustomComboBox<>(newCompanyName);

        //make product id uneditable
        newProductId.setEditable(false);

        //generate dummy list on the composition
        for (int i = 1; i <= 5; i++) {
            compositionDropDownList.add("Composition No " + i);
        }


        newProductCategory.getItems().addAll(Constants.productCategoryList);
        newProductCategory.setValue(Constants.productCategoryList[0]);
        newProductVat.getItems().addAll(Constants.yesNo);
        newProductVat.setValue(Constants.yesNo[1]);

        //Check box and List on Composition Combox Box
        newGenericComposition = new CheckComboBox<String>(compositionDropDownList);
        //Place CheckComboBox on Grid
        newGenericCompGrid.add(newGenericComposition, 0, 0);
        //Code for Generating List of clicked items from the list of ComboBox
        newGenericComposition.getCheckModel().getCheckedItems().
                addListener(new ListChangeListener<String>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends String> change) {
                        updateText(newDisplayComposition, change.getList());
                        while (change.next()) {
                            newDisplayComposition.setText(change.getList().toString());
                            newDisplayComposition.setText(newDisplayComposition.getText().replace("[", ""));
                            newDisplayComposition.setText(newDisplayComposition.getText().replace("]", ""));
                            System.out.println(newDisplayComposition.getText());
                        }

                    }
                });

        selectDatabase();
        productIDIncrement = Integer.valueOf(productIDValueNow.substring(3, productIDValueNow.length()));
        System.out.println(productIDIncrement);
        newProductId.setText(productIDValueNow);
        try {
            preparedStatement = connection.prepareStatement(Constants.selectCompanyNameFromNewProductEntry);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                forNewCompanyNameList.add(resultSet.getString("company_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        newCompanyName.getItems().addAll(forNewCompanyNameList);

    }

    private void selectDatabase() {
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(Constants.productIDGenQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productIDValueNow = resultSet.getString(1);
                System.out.println(productIDValueNow);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePID() {
        String newQuery = Constants.productIDUpdateQuery + "PID" + productIDIncrement + Constants.getProductIDUpdateQueryBack;
        try {
            preparedStatement = connection.prepareStatement(newQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComposition(DragEvent dragEvent) {
    }

    public void addProductBtnClick(ActionEvent event) throws SQLException {
        preparedStatement = connection.prepareStatement(Constants.insertToNewProductEntry);
        preparedStatement.setString(1, newProductId.getText());
        preparedStatement.setString(2, newProductName.getText());
        preparedStatement.setString(3, newDisplayComposition.getText());
        preparedStatement.setString(4, newCompanyName.getEditor().getText());
        preparedStatement.setString(5, newProductGroup.getEditor().getText());
        preparedStatement.setString(6, newProductCategory.getValue());
        preparedStatement.setInt(7, Integer.valueOf(newNumberOfPackPerUnit.getText()));
        if (newProductCategory.getValue().equals("Yes")) {
            preparedStatement.setInt(8, 0);
        } else {
            preparedStatement.setInt(8, 1);
        }
        preparedStatement.executeUpdate();
        CustomAlert customAlert = new CustomAlert("Success", "Your Data is Saved!");
        customAlert.withoutHeader();

        //Clearing
        for (Node node : anchorPane.getChildren()
                ) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
            if (node instanceof ComboBox) {
                ((ComboBox) node).getEditor().clear();
            }
            if (node instanceof CheckComboBox) {
                ((CheckComboBox) node).getItems().clear();
            }
        }
        productIDIncrement++;
        System.out.println(productIDIncrement);
        updatePID();
        selectDatabase();
        newProductId.setText(productIDValueNow);
    }

    private void updateText(Label label, ObservableList<? extends String> list) {
        StringBuilder sb = new StringBuilder();
        sb = CFunctions.updateTextCheckComboBox(sb, label, list);
    }
}