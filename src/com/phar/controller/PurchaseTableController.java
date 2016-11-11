package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.model.Product;
import com.phar.model.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/9/2016.
 */
public class PurchaseTableController {


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
    public PurchaseTableController(){

    }

    @FXML
    private void initialize(){
        ProductImplement productImplement = new ProductImplement();
        productList = productImplement.listProduct();
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
        billNo.setCellValueFactory(new PropertyValueFactory<Product, Integer>("billNo"));
        batch.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productBatchNo"));
        tax.setCellValueFactory(new PropertyValueFactory<Product, Integer>("purchaseTax"));
        purchaseTable.setItems(productList);
    }

    @FXML
    private void addProduct(ActionEvent e){

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
        if(productImplement.addProduct(p)){
            CustomAlert alert = new CustomAlert("Insert Info.", "New Product Saved Successfully");
            alert.withoutHeader();
        }
    }

}
