package com.example.wilder.winstateapp;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;

import static com.facebook.common.logging.FLogDefaultLoggingDelegate.sInstance;

public class UserSingleton {
    public static UserSingleton sInstance = null;
    private String name;
    private String entreprise;
    private String tel;
    private File image;
    private ArrayList<VideoModel> videoModelsList;

    public UserSingleton() {
    }

    public static UserSingleton getsInstance() {
        return sInstance;
    }

    public static void setsInstance(UserSingleton sInstance) {
        UserSingleton.sInstance = sInstance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public ArrayList<VideoModel> getVideoModelsList() {
        return videoModelsList;
    }

    public void setVideoModelsList(ArrayList<VideoModel> videoModelsList) {
        this.videoModelsList = videoModelsList;
    }

    public void removeInstance() {
        sInstance = null;
    }

    public static UserSingleton getInstance() {
        if (sInstance == null) {
            sInstance = new UserSingleton();
        }
        return sInstance;

    }

}
