package com.myprojects.nicklasgilbertsson.meditation.objects;

public class Sounds {
    private String title;

    public Sounds(String title, String song, String subscription) {
        this.title = title;

    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Sounds() {

    }
}
