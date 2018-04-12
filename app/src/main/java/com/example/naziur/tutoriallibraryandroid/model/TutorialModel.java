package com.example.naziur.tutoriallibraryandroid.model;

/**
 * Created by Naziur on 11/03/2018.
 */

public class TutorialModel {

    private String title, author, intro, introImageUrl, createdAtDate, tutorial_id;
    private TagModel[] tags;
    private SectionModel[] sections;
    private String[] references;

    public TutorialModel(String title, String author, String intro, String introImageUrl, String createdAtDate, TagModel[] tags) {
        this.title = title;
        this.author = author;
        this.intro = intro;
        this.introImageUrl = introImageUrl;
        this.createdAtDate = createdAtDate;
        this.tags = tags;
    }

    public String getTutorial_id() {
        return tutorial_id;
    }

    public void setTutorial_id(String tutorial_id) {
        this.tutorial_id = tutorial_id;
    }

    public TutorialModel (String title, String author, String intro, String introImageUrl, String createdAtDate, TagModel[] tags, SectionModel[] sections, String[] references) {
        this(title, author, intro, introImageUrl, createdAtDate, tags);
        this.sections = sections;
        this.references = references;
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

    public TagModel[] getTags() {
        return tags;
    }

    public SectionModel[] getSections() {
        return sections;
    }

    public String[] getReferences() {
        return references;
    }
}
