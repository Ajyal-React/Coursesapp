package com.example.chatapp_cloud.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatapp_cloud.R;

public class Interface extends AppCompatActivity {
    Button student;
    Button lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_interface);
        student = findViewById(R.id.student);
        lecture = findViewById(R.id.lecture);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SiginActivity.class));
            }
        });
        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CheeckActivity.class));
            }
        });
//        lecture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lecture.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivity(new Intent(getApplicationContext(),CheeckActivity.class));
//
//                    }
//                });
//            }
//        });


    }
}