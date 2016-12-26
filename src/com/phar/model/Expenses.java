package com.phar.model;

import javafx.scene.control.Button;

/**
 * Created by Sam on 12/12/2016.
 */

public class Expenses {
    private String expense;
    private String expenseDb;
    private Double amount;
    private Double amountDb;
    private String date;
    private String dateDb;
    private String expenseFromList;


    public String getExpenseFromList() {
        return expenseFromList;
    }

    public void setExpenseFromList(String expenseFromList) {
        this.expenseFromList = expenseFromList;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpenseDb() {
        return expenseDb;
    }

    public void setExpenseDb(String expenseDb) {
        this.expenseDb = expenseDb;
    }

    public Double getAmountDb() {
        return amountDb;
    }

    public void setAmountDb(Double amountDb) {
        this.amountDb = amountDb;
    }

    public String getDateDb() {
        return dateDb;
    }

    public void setDateDb(String dateDb) {
        this.dateDb = dateDb;
    }
}
