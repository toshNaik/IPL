package com.example.navdrawer;

public class Player {
    //TODO: add image player face (image) attribute to this class
    private String Name, Type;
    private Long Price, Rating;

    public Player() {
    }

    public Player(String name, String type, Long price, Long rating) {
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

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public Long getRating() {
        return Rating;
    }

    public void setRating(Long rating) {
        Rating = rating;
    }
}
