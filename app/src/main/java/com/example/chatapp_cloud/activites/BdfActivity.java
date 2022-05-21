package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BdfActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdf);
        floatingActionButton = findViewById(R.id.flat_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UploadBDF.class);
                startActivity(intent);
            }
        });
    }
}