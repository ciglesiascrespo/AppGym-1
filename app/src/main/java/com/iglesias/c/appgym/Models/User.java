package com.iglesias.c.appgym.Models;

public class User {
    private String id;
    private String fingerprint;

    public User() {
    }

    public User(String id, String fingerprint) {
        this.id = id;
        this.fingerprint = fingerprint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}
