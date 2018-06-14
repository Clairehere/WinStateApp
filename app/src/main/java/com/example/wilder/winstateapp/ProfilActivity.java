package com.example.wilder.winstateapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button btnChange = findViewById(R.id.btn_change);
        Button btnValidate = findViewById(R.id.btn_validate);
        final EditText etName = findViewById(R.id.et_name);
        final EditText etEntreprise = findViewById(R.id.et_entreprise);
        final EditText etTel = findViewById(R.id.et_tel);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_entreprise = findViewById(R.id.tv_entreprise);
        TextView tv_tel = findViewById(R.id.tv_tel);
        final UserSingleton userSingleton = UserSingleton.getInstance();
        tv_name.setText("Nom Prénom : "+userSingleton.getName());
        tv_entreprise.setText("Entreprise : "+userSingleton.getEntreprise());
        tv_tel.setText("Tel : " +userSingleton.getTel());

        FloatingActionButton fabBack = findViewById(R.id.fab_profil);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setVisibility(View.VISIBLE);
                etEntreprise.setVisibility(View.VISIBLE);
                etTel.setVisibility(View.VISIBLE);


            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String entreprise = etEntreprise.getText().toString();
                String tel = etTel.getText().toString();
                userSingleton.setName(name);
                userSingleton.setEntreprise(entreprise);
                userSingleton.setTel(tel);
            }
        });

    }
}
