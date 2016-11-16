package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
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

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    CheckComboBox<String> newGenericComposition;
    private final ObservableList<String> strings = FXCollections.observableArrayList();
    String supplierIDValueNow, productIDValueNow;
    Integer supplierIDIncrement, productIDIncrement;

    private List<String> forNewCompanyNameList = new ArrayList<String>();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label newDisplayComposition;
    @FXML
    private GridPane newGenericCompGrid;
    @FXML
    private ComboBox newProductGroup, newCompanyName, newProductVat, newProductCategory;
    @FXML
    private TextField newProductId, newProductName, newNumberOfPackPerUnit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new CustomComboBox<>(newProductGroup);
        new CustomComboBox<>(newCompanyName);


        newProductId.setEditable(false);
        for (int i = 1; i <= 5; i++) {
            strings.add("Composition No " + i);
        }
        // newGenericComposition = new CheckComboBox<String>(strings);
        newProductCategory.getItems().addAll(Constants.productCategoryList);
        newProductCategory.setValue(Constants.productCategoryList[0]);
        newProductVat.getItems().addAll(Constants.yesNo);
        newProductVat.setValue(Constants.yesNo[1]);
        newGenericComposition = new CheckComboBox<String>(strings);
        newGenericCompGrid.add(newGenericComposition, 0, 0);
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
        productIDIncrement++;
        System.out.println(productIDIncrement);
        String newQuery = Constants.productIDUpdateQuery + "PID" + productIDIncrement + Constants.getProductIDUpdateQueryBack;
        System.out.println(newQuery);
        updatePID();
        selectDatabase();
        newProductId.setText(productIDValueNow);

        String sql = "SELECT company_name FROM new_product_entry";
        try {
            preparedStatement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addComposition(DragEvent dragEvent) {
    }

    public void addProductBtnClick(ActionEvent event) throws SQLException {
        preparedStatement = connection.prepareStatement(Constants.addNewProduct);
        preparedStatement.setString(1, newProductId.getText());
        preparedStatement.setString(2, newProductName.getText());
        preparedStatement.setString(3, newDisplayComposition.getText());
        preparedStatement.setString(4, newCompanyName.getEditor().getText());
        preparedStatement.setString(5, newProductGroup.getEditor().getText());
        preparedStatement.setString(6, newProductCategory.getValue().toString());
        preparedStatement.setInt(7, Integer.valueOf(newNumberOfPackPerUnit.getText()));
        if (newProductVat.getValue().toString().equals("Yes")) {
            preparedStatement.setInt(8, 0);
        } else {
            preparedStatement.setInt(8, 1);
        }
        preparedStatement.executeUpdate();
        CustomAlert customAlert = new CustomAlert("Success", "Your Data is Saved!");
        customAlert.withoutHeader();

        //Clearing All TextField
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
        newProductId.setText(productIDValueNow);
    }

    protected void updateText(Label label, ObservableList<? extends String> list) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (list != null) {
            for (int i = 0, max = list.size(); i < max; i++) {
                stringBuilder.append(list.get(i));
                if (i < max - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
    }
}