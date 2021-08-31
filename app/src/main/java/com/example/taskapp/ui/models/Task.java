package com.example.taskapp.ui.models;

import java.io.Serializable;

public class Task implements Serializable {
    private String title,createdAd;

    public Task(String title, String createdAd) {
        this.title = title;
        this.createdAd = createdAd;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAd() {
        return createdAd;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAd(String createdAd) {
        this.createdAd = createdAd;
    }
}

