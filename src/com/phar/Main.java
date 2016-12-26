package com.phar;

import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.DateFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 11/6/2016.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("newSales.fxml"));
        primaryStage.setTitle("Main Menu");
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getMaxX(), Screen.getPrimary().getVisualBounds().getMaxY());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}