package com.phar.controller;

import com.phar.extraFunctionality.CFunctions;
import com.phar.interfaceImplement.UserImplement;
import com.phar.model.SalesAdmin;
import com.phar.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/1/2016.
 */
public class LoginMainController implements Initializable {

    @FXML
    private TextField admin_username;
    @FXML
    private PasswordField admin_password;
    @FXML
    private TextField sales_username;
    @FXML
    private PasswordField sales_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void sales_login(ActionEvent event) {

        SalesAdmin salesAdmin = new SalesAdmin();
        salesAdmin.setUsername(sales_username.getText());
        salesAdmin.setUsername(sales_password.getText());

        UserImplement userImplement = new UserImplement();

        CFunctions.session.put("userName", sales_username.getText());
        CFunctions.session.put("passWord", sales_password.getText());

        if (userImplement.checkSalesAdmin(salesAdmin)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/sales.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setTitle("Sales");
                stage1.setScene(new Scene(root1));
                stage1.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    void admin_login(ActionEvent event) {
        User user = new User();
        user.setUsername(admin_username.getText());
        user.setUsername(admin_password.getText());

        UserImplement userImplement = new UserImplement();

        CFunctions.session.put("userName", admin_username.getText());
        CFunctions.session.put("passWord", admin_password.getText());

        if (userImplement.checkUser(user)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/phar/main.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Main Menu");
                Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getMaxX(), Screen.getPrimary().getVisualBounds().getMaxY());
                System.out.println("X: " + Screen.getPrimary().getVisualBounds().getMaxX());
                System.out.println("Y: " + Screen.getPrimary().getVisualBounds().getMaxY());
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
