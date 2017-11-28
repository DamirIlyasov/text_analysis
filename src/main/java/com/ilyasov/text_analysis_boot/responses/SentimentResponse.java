package com.ilyasov.text_analysis_boot.responses;

public class SentimentResponse {
    private String status;
    private double score;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
