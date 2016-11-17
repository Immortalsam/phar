package com.phar.controller;

/**
 * Created by Sam on 11/10/2016.
 */

import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.NavigationHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private NavigationHandler navigation = new NavigationHandler();

    @FXML
    private Button main_menu_sButton, purchaseEntryBtn, productDetailsBtn, productEntryBtn;

    @FXML
    private Button oldSupplier;

    @FXML
    private Button newSupplier;

    @FXML
    void menuSupplierButton(ActionEvent event) {

        CFunctions.session.put("userName", "");
        CFunctions.session.put("passWord", "");
        navigation.frameNavigation(event, "/com/phar/supplierTable.fxml", "/com/phar/mainMenu.fxml", "Supplier Details", "Main Menu");
    }


    @FXML
    void mainPurchaseButton(ActionEvent event) {
        productDetailsBtn.setOpacity(1);
        purchaseEntryBtn.setOpacity(1);
        productEntryBtn.setOpacity(1);
        oldSupplier.setOpacity(0);
        newSupplier.setOpacity(0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Username : " + CFunctions.session.get("userName", ""));
        System.out.println("Password : " + CFunctions.session.get("passWord", ""));

    }

    public void showOption(MouseEvent mouseEvent) {
        oldSupplier.setOpacity(1);
        newSupplier.setOpacity(1);
        productDetailsBtn.setOpacity(0);
        purchaseEntryBtn.setOpacity(0);
        productEntryBtn.setOpacity(0);
    }


    public void onActionNewSupplier(ActionEvent event) throws IOException {
        navigation.frameNavigation(event, "/com/phar/newSupplier.fxml", "/com/phar/mainMenu.fxml", "Add New Suppliers", "Main Menu");
    }

    public void purchaseEntryClick(ActionEvent event) {
        navigation.frameNavigation(event, "/com/phar/new_purchase_entry.fxml", "/com/phar/mainMenu.fxml", "New Purchase Entry", "Main Menu");
    }

    public void productDetailsClick(ActionEvent event) {
        navigation.frameNavigation(event, "/com/phar/product_table.fxml", "/com/phar/mainMenu.fxml", "Product Details", "Main Menu");
    }

    public void productEntryClick(ActionEvent event) {
        navigation.frameNavigation(event, "/com/phar/productEntry.fxml", "/com/phar/mainMenu.fxml", "Product Entry", "Main Menu");
    }
}

