package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

public class Stock {
    private String stockSymbol;
    private double averagePrice;
    private int shares;

    public Stock(String stockSymbol, double averagePrice, int shares) {
        this.stockSymbol = stockSymbol;
        this.averagePrice = averagePrice;
        this.shares = shares;
    }

    public Stock() {

    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }
}
