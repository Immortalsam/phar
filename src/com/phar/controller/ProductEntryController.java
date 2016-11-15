package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.model.Product;
import com.phar.model.ProductEntry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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
public class ProductEntryController implements Initializable{

    Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> supplierList = new ArrayList<String>();
    private ObservableList<ProductEntry> productList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<ProductEntry> purchaseTable;

    @FXML
    private Label fiscalYear;

    @FXML
    private DatePicker purchaseDate;

    @FXML
    private TextField billNo;

    @FXML
    private ComboBox supplierSearchName;

    @FXML
    private ComboBox cashCredit;

    @FXML
    private ComboBox vat;

    @FXML
    private TableColumn<ProductEntry, String> supplierId;

    @FXML
    private TableColumn<ProductEntry, String> productId;

    @FXML
    private TableColumn<ProductEntry, String > productName;

    @FXML
    private TableColumn<ProductEntry, String> productBatch;

    @FXML
    private TableColumn<ProductEntry, String> productExpDate;

    @FXML
    private TableColumn<ProductEntry, Float> productCcharge;

    @FXML
    private TableColumn<ProductEntry, Integer> productQcfor;

    @FXML
    private TableColumn<ProductEntry, Float> productRate;

    @FXML
    private TableColumn<ProductEntry, Integer> productQuantity;

    @FXML
    private TableColumn<ProductEntry, Float> productAmount;

    @FXML
    private TableColumn<ProductEntry, Float> productMrp;


    @FXML
    private Button addButton;

    @FXML
    private TextField total;

    @FXML
    private TextField discount;

    @FXML
    private TextField netTotal;

    //Default Constructor
    public ProductEntryController(){

    }

    @FXML
    private void addButton(ActionEvent e){

        ProductEntry p = new ProductEntry();
        p.setSupplierId(supplierId.getText());
        p.setProductId(productId.getText());
        p.setProductName(productName.getText());
        p.setProductBatch(productBatch.getText());
        p.setProductExpDate(productExpDate.getText());
        p.setProductCcCharge(Float.valueOf(productCcharge.getText()));
        p.setProductQuFoR(Integer.valueOf(productQcfor.getText()));
        p.setProductRate(Float.valueOf(productRate.getText()));
        p.setProductQuantity(Integer.valueOf(productQuantity.getText()));
        p.setProductAmount(Float.valueOf(productAmount.getText()));
        p.setTodayDate(purchaseDate.getValue().toString());
        p.setBillNo(Integer.valueOf(billNo.getText()));
        p.setProductCashCredit(cashCredit.getValue().toString());
        p.setProductVat(vat.getValue().toString());
        p.setProductMrp(Float.valueOf(productMrp.getText()));

        productList.add(p);

        CustomAlert alert = new CustomAlert("Info","Add successful");
        alert.withoutHeader();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new CustomComboBox<>(supplierSearchName);
        try

        {
            conn = DatabaseConnection.getConnection();
            String selectQuery = "SELECT supplier_name FROM supplier";
            preparedStatement = conn.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (ClassNotFoundException |
                SQLException e)

        {
            e.printStackTrace();
        }
        supplierSearchName.getItems().addAll(supplierList);
        supplierSearchName.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String idQuery = "SELECT supplier_id FROM supplier WHERE supplier_name= '" + supplierSearchName.getValue() + "'";
                System.out.println(idQuery);
                try {
                    preparedStatement = conn.prepareStatement(idQuery);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        supplierId.setText(resultSet.getString("supplier_id"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        productId.textProperty().

                addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!newValue.matches("\\d*")) {
                            productId.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            }
    }
