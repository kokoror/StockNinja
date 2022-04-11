package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

public class User {

  String username;
  String password;
  Location location;
  double cash;

  public User() {
  }

  public User(String username, String password, Location location) {
    this.username = username;
    this.password = password;
    this.location = location;
    this.cash = 10000.0;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public Location getLocation() {
    return this.location;
  }

  public double getCash() {
    return this.cash;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setCash(double cash) {
    this.cash = cash;
  }
}
