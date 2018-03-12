package com.example.naziur.tutoriallibraryandroid.model;

/**
 * Created by Naziur on 11/03/2018.
 */

public class TutorialModel {

    private String title, author, intro, introImageUrl, createdAtDate;
    private String[] tags;

    public TutorialModel(String title, String author, String intro, String introImageUrl, String createdAtDate, String[] tags) {
        this.title = title;
        this.author = author;
        this.intro = intro;
        this.introImageUrl = introImageUrl;
        this.createdAtDate = createdAtDate;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIntro() {
        return intro;
    }

    public String getIntroImageUrl() {
        return introImageUrl;
    }

    public String getCreatedAtDate() {
        return createdAtDate;
    }

    public String[] getTags() {
        return tags;
    }
}
