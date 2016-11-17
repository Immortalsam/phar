package com.phar.extraFunctionality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by somecuitears on 11/17/16.
 */
public class NavigationHandler {
    public void frameNavigation(ActionEvent event, String fxmlLocation, String onExitFxmlLocation, String newTitle, String onExitTitle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocation));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setTitle(newTitle);
            stage1.setScene(new Scene(root1));
            stage1.show();
            stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(onExitFxmlLocation));
                    Parent root1 = null;
                    try {
                        root1 = (Parent) fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage1 = new Stage();
                    stage1.setTitle(onExitTitle);
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                }
            });
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
