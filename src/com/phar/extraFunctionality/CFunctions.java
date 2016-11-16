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

//    public void setSession(String key, String value) {
//        session.put(key, value);
//    }
//
//    public String getSession(String key) {
//        return session.get(key, "");
//    }

    public static final StringBuilder updateTextCheckComboBox(StringBuilder strBuilder, Label label, ObservableList<? extends String> list) {
        final StringBuilder stringBuilder = strBuilder;
        if (list != null) {
            for (int i = 0, max = list.size(); i < max; i++) {
                stringBuilder.append(list.get(i));
                if (i < max - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        return stringBuilder;
    }

    public static ResultSet simpleSelectQuery(PreparedStatement preparedStatement, Connection connection, String query, ResultSet resultSet) {
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
