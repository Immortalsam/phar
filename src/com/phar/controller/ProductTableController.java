package com.phar.controller;

import com.phar.interfaceImplement.ProductDetailsImplement;
import com.phar.model.ProductDetails;
import com.phar.model.Supplier;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/16/2016.
 */
public class ProductTableController{

    @FXML
    private TableView<ProductDetails> pDetailsTable;

    @FXML
    private TableColumn<ProductDetails, String> pid;

    @FXML
    private TableColumn<ProductDetails, String > companyname;

    @FXML
    private TableColumn<ProductDetails, String> pname;

    @FXML
    private TableColumn<ProductDetails, String> compositionname;

    @FXML
    private TableColumn<ProductDetails, String> mgroup;

    @FXML
    private TableColumn<ProductDetails, String> mcategory;

    @FXML
    private TableColumn<ProductDetails, Integer> noup;

    @FXML
    private TableColumn<ProductDetails, BooleanProperty> pvat;

    @FXML
    private TextField searchDetails;

    private ObservableList<ProductDetails> productList;

    //Default Constructor
    public ProductTableController(){

    }

    @FXML
    private void initialize() {
        ProductDetailsImplement productDetailsImplement = new ProductDetailsImplement();
        productList = productDetailsImplement.listSupplier();
        pid.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("productId"));
        companyname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("companyName"));
        pname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("productName"));
        compositionname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("compositionName"));
        mgroup.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("medicineGroup"));
        mcategory.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("medicineCategory"));
        noup.setCellValueFactory(new PropertyValueFactory<ProductDetails, Integer>("noOfPackPerUnit"));
        pvat.setCellValueFactory(new PropertyValueFactory<ProductDetails, BooleanProperty>("vat"));
        pDetailsTable.setItems(productList);
    }

}
