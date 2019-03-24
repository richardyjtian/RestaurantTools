package io.github.richardyjtian.tipcalculator;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String name;
    private int rating;
    public Restaurant(String name, int rating) {
        this.name = name;
        if(rating > 5)
            this.rating = 5;
        else
            this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
