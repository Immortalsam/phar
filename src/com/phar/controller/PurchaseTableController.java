package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DateFormatter;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/9/2016.
 */
public class PurchaseTableController implements Initializable {


    Connection connection;

    private List<String> supplierList = new ArrayList<String>();
    private List<String> completeProductList = new ArrayList<String>();

    @FXML
    private ComboBox searchCombo;

    @FXML
    private TableView<Product> purchaseTable;

    @FXML
    private TableColumn<Product, String> sellerId;

    @FXML
    private TableColumn<Product, Integer> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productQuantity;

    @FXML
    private TableColumn<Product, String> productComposition;

    @FXML
    private TableColumn<Product, String> purchaseDate;

    @FXML
    private TableColumn<Product, String> expDate;

    @FXML
    private TableColumn<Product, String> mfdDate;

    @FXML
    private TableColumn<Product, Float> costPrice;

    @FXML
    private TableColumn<Product, Float> sellingPrice;

    @FXML
    private TableColumn<Product, Integer> billNo;

    @FXML
    private TableColumn<Product, Integer> batch;

    @FXML
    private TableColumn<Product, Integer> tax;

    private ObservableList<Product> productList;


    //For Adding Product
    @FXML
    private TextField sId, pId, pName, pQuantity, pComposition, mDate, eDate, cPrice, sPrice, bNo, pBatch, pTax;

    @FXML
    private DatePicker pDate;


    //Default Constructor
    public PurchaseTableController() throws SQLException, ClassNotFoundException {

    }


    @FXML
    private void addProduct(ActionEvent e) {

        Product p = new Product();
        p.setSellerID(sId.getText());
        p.setProductId(Integer.valueOf(pId.getText()));
        p.setBillNo(Integer.valueOf(bNo.getText()));
        p.setProductName(pName.getText());
        p.setProductQuantity(Integer.valueOf(pQuantity.getText()));
        p.setProductComposition(pComposition.getText());
        p.setProductBatchNo(Integer.valueOf(pBatch.getText()));
        p.setProductMfdDate(mDate.getText());
        p.setProductExpDate(eDate.getText());
        p.setProductCostPrice(Float.valueOf(cPrice.getText()));
        p.setProductSellPrice(Float.valueOf(sPrice.getText()));
        p.setProductPurchaseDate(pDate.getValue().toString());
        p.setPurchaseTax(Integer.valueOf(pTax.getText()));

        ProductImplement productImplement = new ProductImplement();
        if (productImplement.addProduct(p)) {
            CustomAlert alert = new CustomAlert("Insert Info.", "New Product Saved Successfully");
            alert.withoutHeader();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new CustomComboBox<>(searchCombo);

        DateFormatter.dateFormatterForDatePicker(pDate);
        pDate.setValue(DateFormatter.NOW_LOCAL_DATE());

        try {
            connection = DatabaseConnection.getConnection();
            String selectQuery = "SELECT supplier_name FROM supplier";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        searchCombo.getItems().addAll(supplierList);
    }

}
