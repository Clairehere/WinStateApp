package com.example.wilder.winstateapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private VideoView vv;
    private Button bPlay;
    private boolean isPlayed = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String LINK = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";
        VideoView mVideoView  = (VideoView) findViewById(R.id.videoView);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(mVideoView);
        mc.setMediaPlayer(mVideoView);
        Uri video = Uri.parse(LINK);
        mVideoView.setMediaController(mc);
        mVideoView.setVideoURI(video);
        mVideoView.start();

        Uri uriYouTube = Uri.parse(savedInstanceState.getString("https://youtu.be/CP_2T9oTyBc"));

    }
}
