package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.interfaceImplement.ProductDetailsImplement;
import com.phar.model.ProductDetails;
import com.phar.model.Supplier;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Created by Sam on 11/16/2016.
 */
public class ProductTableController extends Application {
    private Connection connection;

    @FXML
    private TableView<ProductDetails> pDetailsTable;

    @FXML
    private TableColumn<ProductDetails, String> pid;

    @FXML
    private TableColumn<ProductDetails, String> companyname;

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
    public ProductTableController() {

    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        connection = DatabaseConnection.getConnection();
        ProductDetailsImplement productDetailsImplement = new ProductDetailsImplement();
        productList = productDetailsImplement.listSupplier(connection);
        pid.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("productId"));
        companyname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("companyName"));
        pname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("productName"));
        compositionname.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("compositionName"));
        mgroup.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("medicineGroup"));
        mcategory.setCellValueFactory(new PropertyValueFactory<ProductDetails, String>("medicineCategory"));
        noup.setCellValueFactory(new PropertyValueFactory<ProductDetails, Integer>("noOfPackPerUnit"));
        pvat.setCellValueFactory(new PropertyValueFactory<ProductDetails, BooleanProperty>("vat"));
        pDetailsTable.setItems(productList);

        pDetailsTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null){
                System.out.println("You cliked a row");
            }
        }));
    }

    public void searchProductDetails(KeyEvent keyEvent) {
        FilteredList<ProductDetails> filteredList = new FilteredList<>(productList, e -> true);
        searchDetails.setOnKeyReleased(e -> {
            searchDetails.textProperty().addListener((observedValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super ProductDetails>) productDetails -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (productDetails.getProductId().contains(newValue)) {
                        return true;
                    } else if (productDetails.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (productDetails.getMedicineCategory().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (productDetails.getMedicineGroup().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ProductDetails> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(pDetailsTable.comparatorProperty());
            pDetailsTable.setItems(sortedList);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }


}
