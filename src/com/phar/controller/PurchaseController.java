package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by somecuitears on 11/8/16.
 */
public class PurchaseController implements Initializable {

    private Product product;
    private ProductImplement productImplement;

    @FXML
    private TextField sellerId, productBillNo, productId, productName, productComposition, productBatch, productMfdDate, productExpDate, productCostPrice, productSellPrice, productQuantity;

    @FXML
    private DatePicker purchaseDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        purchaseDate.setValue(LocalDate.now());

    }

    @FXML
    private void purchaseSaveButton(ActionEvent e) {

        product = new Product();
        product.setSellerID(sellerId.getText());
        product.setProductId(Integer.valueOf(productId.getText()));
        product.setBillNo(Integer.valueOf(productBillNo.getText()));
        product.setProductName(productName.getText());
        product.setProductBatchNo(Integer.valueOf(productBatch.getText()));
        product.setProductMfdDate(productMfdDate.getText());
        product.setProductExpDate(productExpDate.getText());
        product.setProductCostPrice(Float.valueOf(productCostPrice.getText()));
        product.setProductSellPrice(Float.valueOf(productSellPrice.getText()));
        product.setProductComposition(productComposition.getText());
        product.setProductQuantity(Integer.valueOf(productQuantity.getText()));
        product.setProductPurchaseDate(purchaseDate.getValue().toString());


        productImplement = new ProductImplement();
        if (productImplement.addProduct(product)) {

            CustomAlert alert = new CustomAlert("Insert Info.", "New Product Saved Successfully");
            alert.withoutHeader();

        }


    }
}