package com.example.chatapp_cloud.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.adapter.CourseAdapter;
import com.example.chatapp_cloud.models.CourseInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Courses extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Fragment mFragment;
    private Context mContext;
    private Button joinBtn;
    private ArrayList<CourseInfo> coursesList;
    private CourseAdapter courseAdapter = null;
    CourseInfo model;
    SearchView searchView;
    FloatingActionButton addBtn;
    String text;
    public Courses newInstance() {
        return new Courses();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerView = root.findViewById(R.id.coursesRv);
        searchView = root.findViewById(R.id.search);

        mContext = container.getContext();
        addBtn = root.findViewById(R.id.addCourse);

        FirebaseApp.initializeApp(mContext);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddCourse.class));
            }
        });

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        getCourses();
        //search(text);
    }



    private void search(String newText) {
        ArrayList <CourseInfo> filterdList = new ArrayList<>();
        Query query = databaseReference.orderByChild("coursename").startAt(newText.toLowerCase())
                .endAt(newText.toLowerCase()+ "\uf8ff");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    filterdList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final  CourseInfo course = dataSnapshot.getValue(CourseInfo.class);
                        filterdList.add(course);


                    }
                    courseAdapter = new CourseAdapter(mContext,(ArrayList<CourseInfo>) coursesList);
                    courseAdapter.filterList(filterdList);
                    recyclerView.setAdapter(courseAdapter);
                    courseAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(mContext,"Course does not found!",Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void getCourses(){
        coursesList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses");
        DatabaseReference myCourses = FirebaseDatabase.getInstance().getReference().child("mycourses");

        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override

                        public void onDataChange( @NotNull DataSnapshot snapshot) {
                     coursesList.clear();
                  for (DataSnapshot dataSnapshot : snapshot.getChildren()){
               CourseInfo courseInfo = dataSnapshot.getValue(CourseInfo.class);
                coursesList.add(courseInfo);
               courseAdapter = new CourseAdapter(mContext,(ArrayList<CourseInfo>) coursesList);
               recyclerView.setAdapter(courseAdapter);
         courseAdapter.notifyDataSetChanged();

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled( @NotNull DatabaseError error) {
                                                        Toast.makeText(getContext(),"Error: "+error.getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                }

        );
    }



}