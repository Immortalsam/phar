package com.phar.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.phar.custom.CustomAlert;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.GetTime;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sam on 12/1/2016.
 */
public class LoginMainController implements Initializable {

    @FXML
    private JFXTextField admin_username;
    @FXML
    private JFXPasswordField admin_password;
    @FXML
    private TextField sales_username;
    @FXML
    private PasswordField sales_password;

    private String username;
    private String password;
    private final static Logger logger = Logger.getLogger(LoginController.class.getName());


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
        UserImplement userImplement = new UserImplement();
        if(userImplement.checkUser(user)){
            username = admin_username.getText();
            password = admin_password.getText();
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
                GetTime gt = new GetTime();
                logger.log(Level.INFO,"Logged in by '" + admin_username.getText()+ "'" + " at Date '" + LocalDate.now() + "'" + " & at Time '" + gt.timeNow() + "'");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/phar/mainn.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Main Menu");
                    Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getMaxX(), Screen.getPrimary().getVisualBounds().getMaxY());
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
            else{
                CustomAlert ca = new CustomAlert("Some information","Enter Correct Username/Password");
                ca.withoutHeader();
            }
        }
    }
}
