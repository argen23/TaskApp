package com.example.taskapp.ui.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {

    private String title;
    private String createdAd;
    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

