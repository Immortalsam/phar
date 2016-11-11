package com.phar.controller;

/**
 * Created by Sam on 11/10/2016.
 */

import com.phar.extraFunctionality.CFunctions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class MainMenuController implements Initializable {

    @FXML
    private Button main_menu_sButton;

    @FXML
    private Button oldSupplier;

    @FXML
    private Button newSupplier;

    @FXML
    void menuSupplierButton(ActionEvent event) {
        try {

            CFunctions.session.put("userName", "");
            CFunctions.session.put("passWord", "");


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/supplierTable.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Supplier Table Controller");
            stage1.setScene(new Scene(root1));
            stage1.show();
            stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/mainMenu.fxml"));
                    Parent root1 = null;
                    try {
                        root1 = (Parent) fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage1 = new Stage();
                    stage1.setTitle("Supplier Table Controller");
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                }
            });
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void mainPurchaseButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/purchaseTable.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Main Menu");
            stage1.setScene(new Scene(root1));
            stage1.show();
            stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/mainMenu.fxml"));
                    Parent root1 = null;
                    try {
                        root1 = (Parent) fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage1 = new Stage();
                    stage1.setTitle("Add Supplier");
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                }
            });
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Username : " + CFunctions.session.get("userName", ""));
        System.out.println("Password : " + CFunctions.session.get("passWord", ""));

    }

    public void showOption(MouseEvent mouseEvent) {
        oldSupplier.setOpacity(1);
        newSupplier.setOpacity(1);
    }

    public void hideOption(MouseEvent mouseEvent) {
        oldSupplier.setOpacity(0);
        newSupplier.setOpacity(0);
    }

    public void onActionNewSupplier(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/newSupplier.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage1 = new Stage();
        stage1.setTitle("Add New Suppliers");
        stage1.setScene(new Scene(root1));
        stage1.show();
        stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/mainMenu.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage1 = new Stage();
                stage1.setTitle("Main Menu");
                stage1.setScene(new Scene(root1));
                stage1.show();
            }
        });
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}

