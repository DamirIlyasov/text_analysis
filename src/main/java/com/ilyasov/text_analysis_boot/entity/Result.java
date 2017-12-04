package com.ilyasov.text_analysis_boot.entity;

import java.util.List;

/**
 * @author damir
 */
public class Result {
  private List<DataRecord> result;

  public Result(List<DataRecord> result) {
    this.result = result;
  }

  public List<DataRecord> getResult() {
    return result;
  }

  public void setResult(List<DataRecord> result) {
    this.result = result;
  }
}
