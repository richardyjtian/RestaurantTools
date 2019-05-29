package io.github.richardyjtian.tipcalculator;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Dish implements Serializable, Comparable {
    private String name;
    private double price;
    private int rating;
    private String note;

    public Dish(String name, double price, int rating, String note) {
        this.name = name;
        DecimalFormat decimal = new DecimalFormat("0.00");
        this.price = Double.parseDouble(decimal.format(price));
        if(rating > 5)
            this.rating = 5;
        else if(rating < 0)
            this.rating = 0;
        else
            this.rating = rating;
        this.note = note;
    }

    public void setFields(String name, double price, int rating, String note) {
        this.name = name;
        DecimalFormat decimal = new DecimalFormat("0.00");
        this.price = Double.parseDouble(decimal.format(price));
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

    public double getPrice() { return price; }

    public void setPrice(double price) {
        DecimalFormat decimal = new DecimalFormat("0.00");
        this.price = Double.parseDouble(decimal.format(price));
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo(((Dish) o).getName());
    }
}
