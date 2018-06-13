package com.example.wilder.winstateapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 567;
    private static final String TAG = "ConnectActivity";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private ImageView mAvatar;
    private Uri mFileUri;
    private boolean mIsOk;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;
    private Button mButtonCreate;
    private Button mButtonInscript;
    private String mCurrentPhotoPath;

    public static boolean checkAndRequestPermissions(final Activity context) {
        int extstorePermission = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (extstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent register = getIntent();

        //CLICK SUR L'IMAGE :
        mAvatar = findViewById(R.id.image_first);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermissions(ConnectActivity.this)) {
                    dispatchTakePictureIntent();
                }
            }
        });

        mUsername = findViewById(R.id.edit_username);
        mPassword = findViewById(R.id.edit_pass);
        mEmail = findViewById(R.id.edit_email2);

        //CLICK SUR LE BOUTON CREER SON COMPTE :
        mButtonCreate = findViewById(R.id.button_create);
        mButtonInscript = findViewById(R.id.button_inscript);
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUsername.getText().toString();

                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String email = mEmail.getText().toString();



                if (email.matches("") || userName.matches("") || password.matches("") || confirmPassword.matches("")) {
                    Toast.makeText(ConnectActivity.this, R.string.fields_required, Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(ConnectActivity.this, R.string.password_differents, Toast.LENGTH_SHORT).show();
                    }
                    if (password.length() < 6 && confirmPassword.length() < 6) {
                        Toast.makeText(ConnectActivity.this, R.string.pass_numberletter, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ConnectActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String id = mAuth.getCurrentUser().getUid();
                                    //DATABASE :
                                    mDatabaseReference = mDatabase.getReference("Users").child(id);
                                    final UserModel userModel = new UserModel(userName, null);

                                    if (mFileUri != null && !mFileUri.equals("")) {
                                        StorageReference imageRef = FirebaseStorage.getInstance().getReference("Users").child(id).child("imageProfile.jpg");
                                        imageRef.putFile(mFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Uri downloadUri = taskSnapshot.getDownloadUrl();
                                                String avatarUrl = downloadUri.toString();
                                                userModel.setAvatar(avatarUrl);
                                                mDatabaseReference.setValue(userModel);
                                                goToMenu();
                                            }
                                        });
                                    }
                                    else {
                                        mDatabaseReference.setValue(userModel);
                                        goToMenu();
                                    }



                                } else {
                                    Toast.makeText(ConnectActivity.this, R.string.registration_impossible, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        mButtonInscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUsername.getText().toString();

                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String email = mEmail.getText().toString();



                if (email.matches("") || userName.matches("") || password.matches("") || confirmPassword.matches("")) {
                    Toast.makeText(ConnectActivity.this, R.string.fields_required, Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(ConnectActivity.this, R.string.password_differents, Toast.LENGTH_SHORT).show();
                    }
                    if (password.length() < 6 && confirmPassword.length() < 6) {
                        Toast.makeText(ConnectActivity.this, R.string.pass_numberletter, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ConnectActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String id = mAuth.getCurrentUser().getUid();
                                    //DATABASE :
                                    mDatabaseReference = mDatabase.getReference("Users").child(id);
                                    final UserModel userModel = new UserModel(userName, null);

                                    if (mFileUri != null && !mFileUri.equals("")) {
                                        StorageReference imageRef = FirebaseStorage.getInstance().getReference("Users").child(id).child("imageProfile.jpg");
                                        imageRef.putFile(mFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Uri downloadUri = taskSnapshot.getDownloadUrl();
                                                String avatarUrl = downloadUri.toString();
                                                userModel.setAvatar(avatarUrl);
                                                mDatabaseReference.setValue(userModel);
                                                goToMenu();
                                            }
                                        });
                                    }
                                    else {
                                        mDatabaseReference.setValue(userModel);
                                        goToMenu();
                                    }



                                } else {
                                    Toast.makeText(ConnectActivity.this, R.string.registration_impossible, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }

    private void goToMenu(){
        Toast.makeText(ConnectActivity.this, R.string.registration_success, Toast.LENGTH_SHORT).show();
        Intent gotoMenu = new Intent(ConnectActivity.this, MainActivity.class);
        ConnectActivity.this.startActivity(gotoMenu);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
                mFileUri = FileProvider.getUriForFile(this,
                        "com.example.wilder.winstateapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(ConnectActivity.this,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            R.string.need_camera, Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(ConnectActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            R.string.need_storage,
                            Toast.LENGTH_SHORT).show();
                } else {
                    dispatchTakePictureIntent();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                try {
                    if (resultCode == RESULT_OK) {
                        Glide.with(getApplicationContext()).load(mFileUri).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

}
