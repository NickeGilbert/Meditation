package com.myprojects.nicklasgilbertsson.meditation.Objects;

public class Sounds {
    private String title;
    private String song;

    public Sounds(String title, String song) {
        this.title = title;
        this.song = song;

    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Sounds() {

    }
}
