package com.phar;

import com.phar.extraFunctionality.CFunctions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by Sam on 11/6/2016.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainn.fxml"));
        primaryStage.setTitle("Main Menu");
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getMaxX(), Screen.getPrimary().getVisualBounds().getMaxY());
        System.out.println("X: " + Screen.getPrimary().getVisualBounds().getMaxX());
        System.out.println("Y: " + Screen.getPrimary().getVisualBounds().getMaxY());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        System.out.println("Initial " + CFunctions.session.get("userName", ""));
    }
}