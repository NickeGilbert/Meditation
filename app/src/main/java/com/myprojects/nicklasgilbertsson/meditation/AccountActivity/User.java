package com.myprojects.nicklasgilbertsson.meditation.AccountActivity;

public class User {
    public String email;
    public String subscription;
    public String username;
    public String key;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String subscription, String username, String key) {
        this.email = email;
        this.subscription = subscription;
        this.username = username;
        this.key = key;
    }
}
