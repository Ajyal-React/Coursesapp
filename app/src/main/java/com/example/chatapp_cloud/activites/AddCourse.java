package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.adapter.CourseAdapter;
import com.example.chatapp_cloud.models.CourseInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddCourse extends AppCompatActivity {

    private EditText courseName, courseType, courseImage;
    private Button upload,add ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String courseid;
    private Fragment mFragment;
    //RecyclerView recyclerView;
    private ArrayList<CourseInfo> coursesList;
    private CourseAdapter courseAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseName = findViewById(R.id.course_name);
        courseType = findViewById(R.id.course_type);
        courseImage = findViewById(R.id.course_image);
        add = findViewById(R.id.add_btn);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("courses");



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursename = courseName.getText().toString();
                String coursetype = courseType.getText().toString();
                String courseimage = courseImage.getText().toString();
                courseid = coursename;
//                CourseInfo courseInfo = new CourseInfo(courseimage,coursename,coursetype,courseid);
//                courseInfo.setCourseimage(courseimage);
//                courseInfo.setCourseName(coursename);
//                courseInfo.setCourseType(coursetype);
//                DatabaseReference mCourse = databaseReference.child("courses").child(courseid);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot snapshot)
                    {
//                        databaseReference.child(courseid).setValue(courseInfo);

                        Toast.makeText(AddCourse.this, "Course Added Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourse.this, ChatmainActivity.class));

                    }

                    @Override
                    public void onCancelled( @NotNull DatabaseError error) {
                        Toast.makeText(AddCourse.this, "Failed to add course!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}