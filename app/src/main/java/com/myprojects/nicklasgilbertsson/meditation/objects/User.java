package com.myprojects.nicklasgilbertsson.meditation.objects;

public class User {
    public String email;
    public String subscription;
    public String username;

    public User(String email, String subscription, String username) {
        this.email = email;
        this.subscription = subscription;
        this.username = username;
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

    public User() {
    }
}
