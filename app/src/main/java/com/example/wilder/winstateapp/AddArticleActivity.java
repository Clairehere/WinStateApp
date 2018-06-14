package com.example.wilder.winstateapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.LocationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddArticleActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    ArrayList<VideoModel> mVideo = new ArrayList<>();
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        TextView tvdate = findViewById(R.id.tv_date);
        Button btnAdd = findViewById(R.id.btn_add);

        final Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);

        tvdate.setText(stringdate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button camera = findViewById(R.id.camera_button);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              dispatchTakeVideoIntent();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText etDescription = findViewById(R.id.et_description);
                final EditText etLienArticle = findViewById(R.id.lien_article);
                final EditText etName = findViewById(R.id.et_name);

                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String lien = etLienArticle.getText().toString();

                UserSingleton sendArticle = UserSingleton.getInstance();
                String uriString = videoUri.toString();
                String nameUser = sendArticle.getName();

                mVideo.clear();
                VideoModel article = new VideoModel(name, description, uriString, lien, 48.862725,2.287592000000018, 0);
                mVideo.add(article);
                sendArticle.setVideoModelsList(mVideo);

                Intent intent = new Intent(AddArticleActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        final VideoView mVideoView = findViewById(R.id.videoView2);

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            videoUri = intent.getData();
            mVideoView.setVideoURI(videoUri);
            Button camera = findViewById(R.id.camera_button);
            camera.setVisibility(View.GONE);

            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();
            mVideoView.start();

        }

    }

}