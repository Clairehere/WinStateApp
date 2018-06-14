package com.example.wilder.winstateapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectActivity extends AppCompatActivity {

    //Photo
    private Uri mUrlImage;
    private String mCurrentPhotoPath;
    private Uri mPhotoURI;
    private String mUid;

    //CONSTANT
    static final int SELECT_IMAGE = 0;
    private static final int REQUEST_TAKE_PHOTO = 11;
    private static final String ID_PROFIL = "idprofil";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseUser mCurrentUser;
    private StorageReference mStorageRef;


    //Widgets
    private Button btInscript;
    private EditText name;
    private EditText entreprise;
    private ImageView profile;
    private EditText mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton radioButtonLecteur = findViewById(R.id.rb_lecteur);
        final RadioButton radioButtonContributeur = findViewById(R.id.rb_contributeur);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioButtonLecteur.isChecked()){
                    name.setVisibility(View.GONE);
                    entreprise.setVisibility(View.GONE);

                }
                if(radioButtonContributeur.isChecked()){
                    name.setVisibility(View.VISIBLE);
                    entreprise.setVisibility(View.VISIBLE);
                }
            }
        });

        btInscript = findViewById(R.id.btInscript);
        name = findViewById(R.id.etLast);
        entreprise = findViewById(R.id.etFirst);
        profile = findViewById(R.id.ivProfile);
        //mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPickImageDialog();

            }


        });

        btInscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount();


            }
        });

    }

    public void createAccount() {

        final String condition1 = name.getText().toString();
        final String condition2 = entreprise.getText().toString();
        mdp = findViewById(R.id.etTel);

        final UserModel userModel = new UserModel(condition1,null);

        if (TextUtils.isEmpty(condition1) || TextUtils.isEmpty(condition2) ) {

            Toast.makeText(ConnectActivity.this, "Please, fill all fields !", Toast.LENGTH_SHORT).show();
        }
        else if(mdp.length() < 6) {

            Toast.makeText(ConnectActivity.this, "Password 6 character min.", Toast.LENGTH_SHORT).show();
        }
        else {


            mAuth.createUserWithEmailAndPassword(condition1, condition2).addOnCompleteListener(ConnectActivity.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(ConnectActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        final String nameValue = name.getText().toString();
                        final String prenomValue = entreprise.getText().toString();
                        final String id = mAuth.getCurrentUser().getUid();

                        mRef = mFirebaseDatabase.getReference("Profil").child(id);
                        final UserModel userModel = new UserModel(condition1,condition2);

                        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                        final String userID = mCurrentUser.getUid();
                        mRef = FirebaseDatabase.getInstance().getReference("User");
                        mRef.child(userID).child("Profil").child("id").setValue(userID);
                        mRef.child(userID).child("Profil").child("nom").setValue(nameValue);
                        mRef.child(userID).child("Profil").child("entreprise").setValue(prenomValue);

                        Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }
            });
        }
    }




    /*
    -----------------------------------AvatarMethod-------------------------------------------------
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mPhotoURI = FileProvider.getUriForFile(this,
                        "com.example.wilder.winstateapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void showPickImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ConnectActivity.this);
        builderSingle.setTitle("Choisissez une option :");

        final String [] items = new String[] {"Gallerie", "Appareil photo"};
        final Integer[] icons = new Integer[] {R.drawable.gallery, R.drawable.camera_moto_icon};
        ListAdapter adapter = new ArrayAdapterWithIcon(ConnectActivity.this, items, icons);

        builderSingle.setNegativeButton(
                "Annuler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, SELECT_IMAGE);
                                break;

                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }


                    }

                });
        builderSingle.show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_IMAGE:
                if(resultCode == RESULT_OK ) {
                    Uri selectedImage = data.getData();
                    mPhotoURI = selectedImage;
                    Glide.with(ConnectActivity.this).load(mPhotoURI).into(profile);

                    // Glide.with(getApplicationContext()).load(mPhotoURI).apply(RequestOptions.circleCropTransform()).into(profile);
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if(resultCode == RESULT_OK ) {
                    Glide.with(ConnectActivity.this).load(mPhotoURI).into(profile);
                    //Glide.with(getApplicationContext()).load(mPhotoURI).apply(RequestOptions.circleCropTransform()).into(profile);
                }
                break;
        }

    }



}