package com.phar.model;

/**
 * Created by Sam on 12/11/2016.
 */
public class Income {
    private String incomeSource;
    private String incomeSourceDb;
    private Double price;
    private Double priceDb;
    private String date;
    private String dateDb;
    private String incomeFromList;

    public String getIncomeFromList() {
        return incomeFromList;
    }

    public void setIncomeFromList(String incomeFromList) {
        this.incomeFromList = incomeFromList;
    }

    public String getDateDb() {
        return dateDb;
    }

    public void setDateDb(String dateDb) {
        this.dateDb = dateDb;
    }

    public String getIncomeSourceDb() {
        return incomeSourceDb;
    }

    public void setIncomeSourceDb(String incomeSourceDb) {
        this.incomeSourceDb = incomeSourceDb;
    }

    public Double getPriceDb() {
        return priceDb;
    }

    public void setPriceDb(Double priceDb) {
        this.priceDb = priceDb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }
}
