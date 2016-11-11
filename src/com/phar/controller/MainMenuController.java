package com.phar.controller;

/**
 * Created by Sam on 11/10/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button main_menu_sButton;

    @FXML
    void menuSupplierButton(ActionEvent event) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/supplierTable.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setTitle("Main Menu");
                stage1.setScene(new Scene(root1));
                stage1.show();

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    @FXML
    void mainPurchaseButton(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/purchaseTable.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle("Main Menu");
            stage1.setScene(new Scene(root1));
            stage1.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

