package com.example.chatapp_cloud.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.R;
import com.google.firebase.database.DatabaseReference;


public class MyCourses extends Fragment {

    RecyclerView rv;
    DatabaseReference databaseReference;
    public MyCourses() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_my_courses, container, false);

        rv = root.findViewById(R.id.mycoursesRv);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    }
}