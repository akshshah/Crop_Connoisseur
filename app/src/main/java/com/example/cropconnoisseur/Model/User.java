package com.example.cropconnoisseur.Model;

public class User {

    private String id;
    private String username;
    private String imageURL;
    private String number;
    private Double moisture;

    public User(String id, String username, String imageURL, String number, Double moisture) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.number = number;
        this.moisture = moisture;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Double getMoisture() {
        return moisture;
    }

    public void setMoisture(Double moisture) {
        this.moisture = moisture;
    }
}
