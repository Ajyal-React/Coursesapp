package com.example.chatapp_cloud.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.models.CourseInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final ArrayList<CourseInfo> mContentList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String courseid;
    public CourseAdapter(Context mContext, ArrayList<CourseInfo> mContentList) {
        this.mContext = mContext;
        this.mContentList = mContentList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mContentList.get(position).getIsJoined()){
            case 0 :
                return R.layout.fragment_courses;
            case 1:
                return R.layout.fragment_my_courses;
            default:
                return -1;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case R.layout.fragment_courses:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_layout, parent, false);
                return new ViewHolder(view1,viewType);
            case R.layout.fragment_my_courses:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_courses_layout, parent, false);
                return new ViewHolder(view2, viewType);
            default:
                return null;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseName;
        private final TextView courseType;
        private final ImageView courseImage;
        Button join;
        ImageView  update, delete;

        public ViewHolder( @NotNull View itemView, int viewType) {

            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.coursename);
            courseType = (TextView) itemView.findViewById(R.id.coursetype);
            courseImage = (ImageView) itemView.findViewById(R.id.courseimage);
            join = (Button) itemView.findViewById(R.id.join_btn);
            update = (ImageView) itemView.findViewById(R.id.update);
            delete = (ImageView) itemView.findViewById(R.id.delete);


        }

    }

    @Override
    public void onBindViewHolder( @NotNull RecyclerView.ViewHolder mainHolder, int position) {
        ViewHolder holder = (ViewHolder) mainHolder;
        final  CourseInfo model = mContentList.get(position);

        String img = model.getCourseimage();

        if (img!= null && !img.isEmpty()){
            Glide.with(mContext).load(img).fitCenter().into(holder.courseImage);
            model.setCourseimage(img);
        }
        holder.courseName.setText(model.getCourseName());

        holder.courseType.setText(model.getCourseType());

        Button joinBtn = holder.join;
        courseid = model.getCourseId();
        ImageView updateCourse = holder.update;
        ImageView deleteCourse = holder.delete;




        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mycoursesRef = FirebaseDatabase.getInstance().getReference().child("mycourses");
                DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
                mycoursesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        long coursesCount = snapshot.getChildrenCount();
                        if (coursesCount<5){
                            joinBtn.setEnabled(false);
                            model.setIsJoined(1);
                            joinBtn.setText("added");
                        }else{
                            Toast.makeText(mContext, "You have exceeded the limit of courses!", Toast.LENGTH_SHORT).show();
                        }
                        List<CourseInfo> courseInfos = new ArrayList<>();


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });


        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("courses").child(courseid);
                databaseReference.removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }
    public void filterList(ArrayList<CourseInfo> filterllist) {


    }
}
