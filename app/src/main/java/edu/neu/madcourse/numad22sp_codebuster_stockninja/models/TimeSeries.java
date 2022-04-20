package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeSeries implements Parcelable, Comparable<TimeSeries> {

  private String timestamp;
  private double price;

  public TimeSeries() {
  }

  public TimeSeries(String timestamp, double price) {
    this.timestamp = timestamp;
    this.price = price;
  }

  protected TimeSeries(Parcel in) {
    this.timestamp = in.readString();
    this.price = in.readDouble();
  }

  public static final Creator<TimeSeries> CREATOR = new Creator<TimeSeries>() {
    @Override
    public TimeSeries createFromParcel(Parcel in) {
      return new TimeSeries(in);
    }

    @Override
    public TimeSeries[] newArray(int size) {
      return new TimeSeries[size];
    }
  };

  public String getTimestamp() {
    return this.timestamp;
  }

  public double getPrice() {
    return this.price;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.timestamp);
    parcel.writeDouble(this.price);
  }

  @Override
  public int compareTo(TimeSeries timeSeries) {
    return timeSeries.getTimestamp().compareTo(this.getTimestamp());
  }
}
