package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DateFormatter;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private ObservableList<Product> ObvProductList = FXCollections.observableArrayList();

    @FXML
    private Button saveDb;

    @FXML
    private AnchorPane anchorPane;

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

        ObvProductList.add(p);

        sellerId.setCellValueFactory(new PropertyValueFactory<Product, String>("sellerID"));
        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productQuantity"));
        productComposition.setCellValueFactory(new PropertyValueFactory<Product, String>("productComposition"));
        purchaseDate.setCellValueFactory(new PropertyValueFactory<Product, String>("productPurchaseDate"));
        expDate.setCellValueFactory(new PropertyValueFactory<Product, String>("productExpDate"));
        mfdDate.setCellValueFactory(new PropertyValueFactory<Product, String>("productMfdDate"));
        costPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("productCostPrice"));
        sellingPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("productSellPrice"));
        batch.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productBatchNo"));
        tax.setCellValueFactory(new PropertyValueFactory<Product, Integer>("purchaseTax"));
        billNo.setCellValueFactory(new PropertyValueFactory<Product, Integer>("billNo"));

        purchaseTable.setItems(ObvProductList);
        searchCombo.setDisable(true);
        sId.setDisable(true);
        bNo.setDisable(true);
        //Clearing All TextField
        for (Node node : anchorPane.getChildren()
                ) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
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
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        searchCombo.getItems().addAll(supplierList);
        searchCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String idQuery = "SELECT supplier_id FROM supplier WHERE supplier_name= '" + searchCombo.getValue() + "'";
                System.out.println(idQuery);
                try {
                    preparedStatement = connection.prepareStatement(idQuery);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        sId.setText(resultSet.getString("supplier_id"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        pId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    pId.setText(newValue.replaceAll("[^\\d]", ""));
                }
              
            }
        });

    }

    public void saveToDatabase(ActionEvent event) throws SQLException {
        String insertQuery = "INSERT INTO product_from_supplier VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        for (Product p : ObvProductList
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, p.getSellerID().toString());
            preparedStatement.setInt(2, p.getProductId());
            preparedStatement.setString(3, p.getProductName());
            preparedStatement.setInt(4, p.getProductQuantity());
            preparedStatement.setString(5, p.getProductComposition());
            preparedStatement.setString(6, p.getProductPurchaseDate());
            preparedStatement.setString(7, p.getProductMfdDate());
            preparedStatement.setString(8, p.getProductExpDate());
            preparedStatement.setFloat(9, p.getProductCostPrice());
            preparedStatement.setFloat(10, p.getProductSellPrice());
            preparedStatement.setInt(11, p.getBillNo());
            preparedStatement.setInt(12, p.getProductBatchNo());
            preparedStatement.setInt(13, p.getPurchaseTax());
            preparedStatement.executeUpdate();
        }

        searchCombo.setDisable(false);
        bNo.setDisable(false);

        CustomAlert alert = new CustomAlert("Insert to database Info", "Save successful");
        alert.withoutHeader();
    }

}
