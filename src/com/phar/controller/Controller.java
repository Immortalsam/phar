package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.interfaceImplement.SupplierImplement;
import com.phar.model.Product;
import com.phar.model.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Sam on 11/6/2016.
 */

public class Controller {

     //New COmmments
    private Supplier supplier;
    private SupplierImplement supplierImplement;
    private Product product;
    private ProductImplement productImplement;


    //For main.fxml
    @FXML
    private TextField supplierId, supplierName, supplierAddress, supplierContact, supplierCategory, supplierPanNo;

    //For purchaseEntry.fxml
    @FXML
    private TextField productBillNo, productId, productName, productComposition, productBatch, productMfdDate,productExpDate,productCostPrice,productSellPrice, productQuantity;

    @FXML
    private DatePicker  purchaseDate;

    @FXML
    private AnchorPane pane;

    @FXML
    private void onClickSave(ActionEvent e){

        supplier = new Supplier();
        supplier.setSupplierId(supplierId.getText());
        supplier.setSupplierName(supplierName.getText());
        supplier.setSupplierAddress(supplierAddress.getText());
        supplier.setSupplierContact(supplierContact.getText());
        supplier.setSupplierCategory(supplierCategory.getText());
        supplier.setPanNo(Double.valueOf(supplierPanNo.getText()));

        supplierImplement = new SupplierImplement();
        if(supplierImplement.addSupplier(supplier)){

            CustomAlert alert = new CustomAlert("Insert info", "New Supplier Saved Successfully");
            alert.withoutHeader();
            for (Node node : pane.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).clear();
                }
            }
        }
    }

    @FXML
    private void purchaseSaveButton(ActionEvent e){

        product = new Product();
        product.setBillNo(productBillNo.getText());
        product.setProductId(productId.getText());
        product.setProductName(productName.getText());
        product.setProductBatchNo(productBatch.getText());
        product.setProductMfdDate(productMfdDate.getText());
        product.setProductExpDate(productExpDate.getText());
        product.setProductCostPrice(productCostPrice.getText());
        product.setProductSellPrice(productSellPrice.getText());
        product.setProductComposition(productComposition.getText());
        product.setProductQuantity(productQuantity.getText());
        product.setProductPurchaseDate(purchaseDate.getValue().toString());


        productImplement = new ProductImplement();
        if(productImplement.addProduct(product)){

            CustomAlert alert = new CustomAlert("Insert Info.", "New Product Saved Successfully");
            alert.withoutHeader();

        }


    }

}
