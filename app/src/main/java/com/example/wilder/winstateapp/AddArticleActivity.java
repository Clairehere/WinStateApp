package com.example.wilder.winstateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        TextView tvdate = findViewById(R.id.tv_date);
        final EditText etName  =findViewById(R.id.et_name);
        final EditText etDescription = findViewById(R.id.et_description);
        Button btnAdd = findViewById(R.id.btn_add);

        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);

        tvdate.setText(stringdate);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                //TODO : envoie a firebase
            }
        });


    }

}