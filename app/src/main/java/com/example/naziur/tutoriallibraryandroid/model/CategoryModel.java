package com.example.naziur.tutoriallibraryandroid.model;

/**
 * Created by Hamidur on 11/03/2018.
 */

public class CategoryModel {
    private int type = 0;

    private String text;
    private String id;

    public CategoryModel(String text) {
        this.text = text;
    }

    public CategoryModel (String text, String id) {
        this.text = text;
        this.id = id;
        type = 1;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
