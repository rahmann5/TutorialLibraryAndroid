package com.example.naziur.tutoriallibraryandroid.model;

/**
 * Created by Hamidur on 12/03/2018.
 */

public class TagModel {

    private String tagName, tagId;

    public TagModel(String tagName, String tagId) {
        this.tagName = tagName;
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagId() {
        return tagId;
    }
}
