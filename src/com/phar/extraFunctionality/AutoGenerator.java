package com.phar.extraFunctionality;

import com.phar.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by somecuitears on 11/23/16.
 */
public class AutoGenerator {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String query, ID;
    private Integer newID;
//    private String productIDValueNow;
//    private Integer productIDIncrement;


//    public static void main(String[] args) {
//        AutoGenerator g = new AutoGenerator();
//        System.out.println(g.CurrentID("BID"));
//        System.out.println("========== Value Update ===========");
//        System.out.println(g.NewID("BID"));
//
//    }

    public String CurrentID(String ofID) {
        //ofID = "CID";
        try {
            connection = DatabaseConnection.getConnection();
            if (ofID.equals("CID")) {
                query = "SELECT value FROM idGen WHERE ofID = 'customerIDGen'";
            } else if (ofID.equals("PID")) {
                query = "SELECT value FROM idGen WHERE ofID = 'productIDGen'";
            } else if (ofID.equals("SID")) {
                query = "SELECT value FROM idGen WHERE ofID = 'supplierIDGen'";
            } else if (ofID.equals("BID")) {
                query = "SELECT value FROM idGen WHERE ofID = 'billNoGen'";
            } else {
                System.out.println("ERRRORRRR");
            }
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.next());
                ID = resultSet.getString("value");
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return ID;
    }

    public String NewID(String ofID) {
        ID = CurrentID(ofID);
        newID = Integer.valueOf(ID.substring(3));
        newID++;
        try {
            connection = DatabaseConnection.getConnection();
            if (ofID.equals("CID")) {
                query = "UPDATE idGen SET value= '" + ofID + newID + "' WHERE ofID ='customerIDGen'";
            } else if (ofID.equals("PID")) {
                query = "UPDATE idGen SET value= '" + ofID + newID + "' WHERE ofID ='productIDGen'";
            } else if (ofID.equals("SID")) {
                query = "UPDATE idGen SET value= '" + ofID + newID + "' WHERE ofID ='supplierIDGen'";
            } else if (ofID.equals("BID")) {
                query = "UPDATE idGen SET value= '" + ofID + newID + "' WHERE ofID ='billNoGen'";
            } else {
                System.out.println("Errroorrr");
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return ofID + newID;
    }
}
