package com.myprojects.nicklasgilbertsson.meditation.Objects;

import android.util.Log;

public class User {
    public String email;
    public String subscription;
    public String username;
    public String key;

    public User(String email, String subscription, String username, String key) {
        this.email = email;
        this.subscription = subscription;
        this.username = username;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public User() {
    }
}
