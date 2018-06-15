package com.example.wilder.winstateapp;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

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
        final Button btnAdd = findViewById(R.id.btn_add);
        ImageView btnDelete = findViewById(R.id.iv_delete);
        RadioGroup radioGroup = findViewById(R.id.radioGroup2);
        final RadioButton rbSocial = findViewById(R.id.rb_social);
        final RadioButton rbEcolo = findViewById(R.id.rb_ecolo);
        final RadioButton rbTechno = findViewById(R.id.rb_techno);
        final RadioButton rbEconomie = findViewById(R.id.tb_economie);
        final String[] theme = {""};
        VideoView mVideoView = findViewById(R.id.videoView2);

        final Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);

        mVideoView.setVisibility(View.GONE);

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
        btnAdd.setVisibility(View.VISIBLE);
              dispatchTakeVideoIntent();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddArticleActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(rbSocial.isChecked()){
                    theme[0] = "Social";
                }
                if(rbEcolo.isChecked()){
                    theme[0] = "Ecologie";
                }
                if(rbEconomie.isChecked()){
                    theme[0] = "Economie";

                }
                if(rbTechno.isChecked()){
                    theme[0] = " Technologie";
                }

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
                double latUser;
                double longUser;

                latUser =  sendArticle.getLatUser();
                longUser = sendArticle.getLongUser();

                mVideo.clear();
                VideoModel article = new VideoModel(name, description, uriString, lien,theme[0], latUser,longUser, 0);
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
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,2);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        VideoView mVideoView = findViewById(R.id.videoView2);
        mVideoView.setVisibility(View.VISIBLE);

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