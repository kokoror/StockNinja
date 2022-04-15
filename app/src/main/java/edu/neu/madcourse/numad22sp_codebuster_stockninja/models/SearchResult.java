package edu.neu.madcourse.numad22sp_codebuster_stockninja.models;

public class SearchResult {

  private String symbol;
  private String companyName;

  public SearchResult() {
  }

  public SearchResult(String symbol, String companyName) {
    this.symbol = symbol;
    this.companyName = companyName;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
