package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

import java.util.Objects;

public class Location {

  String city;
  String state;
  String country;

  public Location() {
  }

  public Location(String city, String state, String country) {
    this.city = city;
    this.state = state;
    this.country = country;
  }

  public String getCity() {
    return this.city;
  }

  public String getState() {
    return this.state;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(this.getCity(), location.getCity()) && Objects
        .equals(this.getState(), location.getState()) && Objects
        .equals(this.getCountry(), location.getCountry());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getCity(), this.getState(), this.getCountry());
  }
}
