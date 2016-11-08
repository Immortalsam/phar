package com.phar;

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
        Parent root = FXMLLoader.load(getClass().getResource("purchaseEntry.fxml"));
        primaryStage.setTitle("Purchase Entry!!");

        Screen screen = Screen.getPrimary();
        Rectangle2D bound = screen.getVisualBounds();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setX(bound.getMinX());
        primaryStage.setY(bound.getMinY());
        primaryStage.setWidth(bound.getWidth());
        primaryStage.setWidth(bound.getHeight());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}





