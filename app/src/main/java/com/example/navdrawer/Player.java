package com.example.navdrawer;

public class Player {
    //TODO: add image player face (image) attribute to this class
    private String name, type, imageUri;
    private int price, rating;
    private boolean uncapped;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isUncapped() {
        return uncapped;
    }

    public void setUncapped(boolean uncapped) {
        this.uncapped = uncapped;
    }

    public Player() {
    }

    public Player(String name, String type, String imageUri, int price, int rating, boolean uncapped) {
        this.name = name;
        this.type = type;
        this.imageUri = imageUri;
        this.price = price;
        this.rating = rating;
        this.uncapped = uncapped;
    }
}
