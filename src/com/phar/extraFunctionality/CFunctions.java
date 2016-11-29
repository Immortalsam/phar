package com.phar.extraFunctionality;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;

/**
 * Created by somecuitears on 11/8/16.
 */
public class CFunctions {

    public static final Preferences session = Preferences.userNodeForPackage(com.phar.extraFunctionality.CFunctions.class);

    public static StringBuilder updateTextCheckComboBox(StringBuilder strBuilder, Label label, ObservableList<? extends String> list) {
        if (list != null) {
            for (int i = 0, max = list.size(); i < max; i++) {
                strBuilder.append(list.get(i));
                if (i < max - 1) {
                    strBuilder.append(", ");
                }
            }
        }
        return strBuilder;
    }

    public static ResultSet executeQuery(PreparedStatement preparedStatement, Connection connection, String query, ResultSet resultSet) {
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Restrict Text Field to input Number
    public static void restrictTxtField(TextField textField, String regex) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex)) {
                textField.setText(oldValue);
            }
        });
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
