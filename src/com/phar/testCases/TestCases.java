package com.phar.testCases;

import com.phar.custom.CustomAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.scene.control.ButtonType.OK;

/**
 * Created by Sam on 11/20/2016.
 */
public abstract class TestCases {

    public static void checkEmptinessAndString(TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                newValue = tf.getText();
                if (newValue != null && newValue.trim().isEmpty()) {
                    CustomAlert alert = new CustomAlert("Alert", "Please enter a value");
                    alert.withoutHeader();
                } else if (newValue.matches("[A-Z].") || newValue.matches("[a-z].")) {
                    CustomAlert alert = new CustomAlert("Alert", "Please only enter numeric value");
                    alert.withoutHeader();
                }
            }
        });
    }

    public static void checkEmptinessAndInteger(TextField tf) {
        // tf.textProperty().addListener(new ChangeListener<String>() {
        tf.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("Entered");
                if (tf.getText().matches("[0-9].")) {
                    System.out.println("Entered Ayyo");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Info", OK);
                    alert.getButtonTypes().add(OK);
                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            tf.setText("");
                        }
                    });

                }
            }
        });
    }

    public static void TabAndCheck(TextField tf) {
        tf.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode().equals(KeyCode.TAB)) {
                    System.out.println("TAb pressed");
                    checkEmptinessAndInteger(tf);
                }
//                        Node node = (Node) event.getSource();
//                        if (node instanceof TextField) {
//                            TextFieldSkin skin = (TextFieldSkin) ((TextField)node).getSkin();
//                            if (event.isShiftDown()) {
//                                skin.getBehavior().traversePrevious();
//                            }
//                            else {
//                                skin.getBehavior().traverseNext();
//                            }
//                        }
//                        else if (node instanceof TextArea) {
//                            TextAreaSkin skin = (TextAreaSkin) ((TextArea)node).getSkin();
//                            if (event.isShiftDown()) {
//                                skin.getBehavior().traversePrevious();
//                            }
//                            else {
//                                skin.getBehavior().traverseNext();
//                            }
//                        }
//
//                        event.consume();
//                    }
            }
        });
    }
}
