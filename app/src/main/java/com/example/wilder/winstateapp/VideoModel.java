package com.example.wilder.winstateapp;

import java.util.ArrayList;

public class VideoModel {
    private String title;
    private String description;
    private String linkVideo;
    private String linkArticle;
    private String theme;
    double latitude;
    double longitude;
    int fakenew;

    public VideoModel(String title, String description, String linkVideo, String linkArticle, String theme, double latitude, double longitude, int fakenew) {
        this.title = title;
        this.description = description;
        this.linkVideo = linkVideo;
        this.linkArticle = linkArticle;
        this.theme = theme;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fakenew = fakenew;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getLinkArticle() {
        return linkArticle;
    }

    public void setLinkArticle(String linkArticle) {
        this.linkArticle = linkArticle;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFakenew() {
        return fakenew;
    }

    public void setFakenew(int fakenew) {
        this.fakenew = fakenew;
    }
}
