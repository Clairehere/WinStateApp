package com.example.wilder.winstateapp;

import java.util.ArrayList;

public class VideoModel {
    private String title;
    private String description;
    private String linkVideo;
    private String LinkArticle;
    private String contributeur;
    double latitude;
    double longitude;
    int fakenew;
    //ArrayList<String> tagList;

    public VideoModel(String title, String description, String linkVideo, String linkArticle, String contributeur, double latitude, double longitude, int fakenew) {
        this.title = title;
        this.description = description;
        this.linkVideo = linkVideo;
        LinkArticle = linkArticle;
        this.contributeur = contributeur;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fakenew = fakenew;
    }

    public VideoModel() {

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
        return LinkArticle;
    }

    public void setLinkArticle(String linkArticle) {
        LinkArticle = linkArticle;
    }

    public String getContributeur() {
        return contributeur;
    }

    public void setContributeur(String contributeur) {
        this.contributeur = contributeur;
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

    /**
    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }
    */
}
