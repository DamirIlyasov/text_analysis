package com.ilyasov.text_analysis_boot.requestPojos;

/**
 * @author Damir Ilyasov
 */
public class RequestPojo {
  private String text;
  private String date;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
