package com.phar.custom;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Sam on 11/6/2016.
 */

public class CustomAlert {

    private String title;
    private String content;
    private Alert alert;


    public CustomAlert(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void withoutHeader() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void withTwoOptions() {

    }
}
