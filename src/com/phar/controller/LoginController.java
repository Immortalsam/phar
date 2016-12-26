package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.extraFunctionality.GetTime;
import com.phar.interfaceImplement.UserImplement;
import com.phar.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private TextField usName;

    @FXML
    private PasswordField usPass;

    private String username;
    private String password;
    private final static Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    void onClickSignIn(ActionEvent event) {
        User user = new User();
        UserImplement userImplement = new UserImplement();
        if(userImplement.checkUser(user)){
            username = usName.getText();
            password = usPass.getText();
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
                GetTime gt = new GetTime();
                logger.log(Level.INFO,"Logged in by '" + usName.getText()+ "'" + " at Date '" + LocalDate.now() + "'" + " & at Time '" + gt.timeNow() + "'");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}