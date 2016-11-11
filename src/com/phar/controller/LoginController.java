package com.phar.controller;

import com.phar.interfaceImplement.UserImplement;
import com.phar.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

        @FXML
        private TextField usName;

        @FXML
        private PasswordField usPass;

        @FXML
        void onClickSignIn(ActionEvent event) {
            User user = new User();
            user.setUsername(usName.getText());
            user.setUsername(usPass.getText());

            UserImplement userImplement = new UserImplement();

            if (userImplement.checkUser(user)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/phar/mainMenu.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage1 = new Stage();
                    stage1.setTitle("Main Menu");
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

            @Override
            public void initialize(URL location, ResourceBundle resources) {

            }
}

