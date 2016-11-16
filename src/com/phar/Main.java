package com.phar;

import com.phar.extraFunctionality.CFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by Sam on 11/6/2016.
 */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("new_purchase_entry.fxml"));
        primaryStage.setTitle("Admin Login");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Initial " + CFunctions.session.get("userName", ""));
    }


    public static void main(String[] args) {
        launch(args);
    }
}