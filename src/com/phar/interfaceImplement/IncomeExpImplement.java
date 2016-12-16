package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.IncomeExpInterface;
import com.phar.model.Expenses;
import com.phar.model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 12/12/2016.
 */
public class IncomeExpImplement implements IncomeExpInterface {

    private PreparedStatement preparedStatement;
    private Connection connection;

    public IncomeExpImplement(){
        try
        {
            connection = DatabaseConnection.getConnection();
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean addIncome(Income in) {
        String query = "INSERT into income(date,income_source, price) VALUES (?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, in.getDate());
            preparedStatement.setString(2, in.getIncomeFromList());
            preparedStatement.setDouble(3, in.getPrice());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addExpense(Expenses ex) {
        String query = "INSERT into expense(date,expense_source, amount) VALUES (?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ex.getDate());
            preparedStatement.setString(2, ex.getExpenseFromList());
            preparedStatement.setDouble(3, ex.getAmount());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
