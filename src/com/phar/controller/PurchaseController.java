package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
    private GridPane gridPane;

    @FXML
    private TextField sellerId, productBillNo, productId, productName, productComposition, productBatch, productMfdDate, productExpDate, productCostPrice, productSellPrice, productQuantity, purchaseTax;

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
        product.setProductQuantity(Integer.valueOf(productQuantity.getText()));
        product.setProductComposition(productComposition.getText());
        product.setProductBatchNo(Integer.valueOf(productBatch.getText()));
        product.setProductMfdDate(productMfdDate.getText());
        product.setProductExpDate(productExpDate.getText());
        product.setProductCostPrice(Float.valueOf(productCostPrice.getText()));
        product.setProductSellPrice(Float.valueOf(productSellPrice.getText()));
        product.setProductPurchaseDate(purchaseDate.getValue().toString());
        product.setPurchaseTax(Integer.valueOf(purchaseTax.getText()));

        System.out.println(product.getSellerID() + " : " + product.getProductId() + " : " + product.getBillNo() + " : " + product.getProductName()
                + " : " + product.getProductQuantity() + " : " + product.getProductComposition() + " : " + product.getProductBatchNo() + " : " + product.getProductMfdDate()
                + " : " + product.getProductExpDate() + " : " + product.getProductCostPrice() + " : " + product.getProductSellPrice()
                + " : " + product.getProductPurchaseDate() + " : " + product.getPurchaseTax());
        productImplement = new ProductImplement();
        if (productImplement.addProduct(product)) {

            CustomAlert alert = new CustomAlert("Insert Info.", "New Product Saved Successfully");
            alert.withoutHeader();

        }
    }

    @FXML
    public void clickCancelBtn(ActionEvent event) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }
    }
}
