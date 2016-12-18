package com.phar.controller;

import com.mysql.jdbc.log.Log;
import com.phar.extraFunctionality.CFunctions;
import com.phar.interfaceImplement.UserImplement;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private TextField usName;

    @FXML
    private PasswordField usPass;

    private final static Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    void onClickSignIn(ActionEvent event) {
        User user = new User();
        user.setUsername(usName.getText());
        user.setUsername(usPass.getText());

        UserImplement userImplement = new UserImplement();

        CFunctions.session.put("userName", usName.getText());
        CFunctions.session.put("passWord", usPass.getText());

        if (userImplement.checkUser(user)) {
            logger.log(Level.INFO,"Logged in by '" + usName.getText()+ "'");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/phar/mainn.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}