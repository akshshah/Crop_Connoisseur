package com.example.cropconnoisseur.Model;

public class MoistureData {

    private String date;
    private String comment;
    private String moisture;

    public MoistureData(String date, String comment, String moisture) {
        this.date = date;
        this.comment = comment;
        this.moisture = moisture;
    }

    public MoistureData() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }
}
