package com.phar.extraFunctionality;

import javafx.scene.Node;
import javafx.scene.control.TextField;

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
}
