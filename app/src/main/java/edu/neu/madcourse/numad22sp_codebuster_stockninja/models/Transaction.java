package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

import java.time.Instant;

public class Transaction {

  String username;
  String symbol;
  double price;
  double shares;
  Instant timestamp;

  public Transaction() {
  }

  public Transaction(String username, String symbol, double price, double shares,
      Instant timestamp) {
    this.username = username;
    this.symbol = symbol;
    this.price = price;
    this.shares = shares;
    this.timestamp = timestamp;
  }

  public String getUsername() {
    return this.username;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public double getPrice() {
    return this.price;
  }

  public double getShares() {
    return this.shares;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setShares(double shares) {
    this.shares = shares;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}
