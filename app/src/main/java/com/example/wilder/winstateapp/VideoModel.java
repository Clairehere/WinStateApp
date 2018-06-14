package com.example.wilder.winstateapp;

import java.util.ArrayList;

public class VideoModel {
    private String title;
    private String description;
    private String linkVideo;
    private String contributeur;
    private String date;
    int latitude;
    int longitude;
    int fakenew;
    ArrayList<String> tagList;


    public VideoModel(String title, String description, String linkVideo, String contributeur, String date, int latitude, int longitude, int fakenew, ArrayList<String> tagList) {
        this.title = title;
        this.description = description;
        this.linkVideo = linkVideo;
        this.contributeur = contributeur;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fakenew = fakenew;
        this.tagList = tagList;
    }

    public VideoModel(){}

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

    public String getContributeur() {
        return contributeur;
    }

    public void setContributeur(String contributeur) {
        this.contributeur = contributeur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getFakenew() {
        return fakenew;
    }

    public void setFakenew(int fakenew) {
        this.fakenew = fakenew;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }
}
