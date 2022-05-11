package com.example.chatapp_cloud.activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.fragment.Courses;
import com.example.chatapp_cloud.fragment.MyCourses;
import com.example.chatapp_cloud.fragment.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Button joinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainchating);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.courses);



    }
    Courses courses = new Courses();
    MyCourses myCourses = new MyCourses();
    Profile profile = new Profile();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected( @NotNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.courses:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, courses).commit();
                return true;

            case R.id.my_courses:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, myCourses).commit();
                return true;



            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profile).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}