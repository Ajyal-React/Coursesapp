package com.example.chatapp_cloud.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.adapter.UserCourseAdapter;
import com.example.chatapp_cloud.models.CourseInfo;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MyCourses extends Fragment {
     RecyclerView rv;
     DatabaseReference databaseReference;
     FirebaseDatabase firebaseDatabase;
    private ArrayList<CourseInfo> coursesList;
    private UserCourseAdapter userCourseAdapter = null;
    CourseInfo courseInfo;
    private Context mContext;


    public MyCourses() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_my_courses, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("mycourses");
        rv = root.findViewById(R.id.mycoursesRv);
        mContext = container.getContext();
        FirebaseApp.initializeApp(mContext);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getmyCourses();

    }

    private void getmyCourses() {

        coursesList = new ArrayList<>();
        rv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setNestedScrollingEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("mycourses");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                coursesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CourseInfo courseInfo = dataSnapshot.getValue(CourseInfo.class);
                    coursesList.add(courseInfo);
                    userCourseAdapter = new UserCourseAdapter(mContext, (ArrayList<CourseInfo>) coursesList);
                    rv.setAdapter(userCourseAdapter);
                    userCourseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled( @NotNull DatabaseError error) {
                Toast.makeText(getContext(),"Error: "+error.getMessage(),Toast.LENGTH_SHORT).show();

            }


        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}