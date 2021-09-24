package com.example.covidscanner.ui.symptom;

public class SymptomsModel {

    private String name;
    private String rating;

    public SymptomsModel() {
    }

    public SymptomsModel(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
