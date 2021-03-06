package io.github.richardyjtian.tipcalculator;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable, Comparable{
    private String name;
    private String location;
    private String type;
    private int rating;
    private String note;
    private ArrayList<Dish> dishes = new ArrayList<Dish>();

    public Restaurant(String name, String location, String type, int rating, String note) {
        this.name = name;
        this.location = location;
        this.type = type;
        if(rating > 5)
            this.rating = 5;
        else if(rating < 0)
            this.rating = 0;
        else
            this.rating = rating;
        this.note = note;
    }

    public void setFields(String name, String location, String type, int rating, String note) {
        this.name = name;
        this.location = location;
        this.type = type;
        if(rating > 5)
            this.rating = 5;
        else if(rating < 0)
            this.rating = 0;
        else
            this.rating = rating;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Restaurant) o).getName());
    }
}

