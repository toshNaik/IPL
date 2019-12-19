package com.example.navdrawer;

public class Player {
    //TODO: add image player face (image) attribute to this class
    private String Name, Type;
    private int Price, Rating;

    public Player() {
    }

    public Player(String name, String type, int price, int rating) {
        Name = name;
        Type = type;
        Price = price;
        Rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
