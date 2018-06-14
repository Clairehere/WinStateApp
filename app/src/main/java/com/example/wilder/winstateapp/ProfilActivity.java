package com.example.wilder.winstateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button btnChange = findViewById(R.id.btn_change);
        final EditText etName = findViewById(R.id.et_name);
        final EditText etEntreprise = findViewById(R.id.et_entreprise);
        final EditText etTel = findViewById(R.id.et_tel);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String entreprise = etEntreprise.getText().toString();
                String tel = etTel.getText().toString();

            }
        });
    }
}
