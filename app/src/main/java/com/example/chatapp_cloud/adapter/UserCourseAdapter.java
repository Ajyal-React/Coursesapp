package com.example.chatapp_cloud.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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


public class UserCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final ArrayList<CourseInfo> mContentList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String courseid;

    public UserCourseAdapter(Context mContext, ArrayList<CourseInfo> mContentList) {
        this.mContext = mContext;
        this.mContentList = mContentList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mContentList.get(position).getIsJoined()) {
            case 0:
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
                return new mViewHolder(view1, 0);
            case R.layout.fragment_my_courses:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_courses_layout, parent, false);
                return new mViewHolder(view2, 1);
            default:
                return null;
        }

    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final TextView courseType;
        private final ImageView courseImage;
        Button join,left,showCourse;
        ImageView update, delete;


        public mViewHolder(@NotNull View itemView, int viewType) {

            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.coursename);
            courseType = (TextView) itemView.findViewById(R.id.coursetype);
            courseImage = (ImageView) itemView.findViewById(R.id.courseimage);
            join = (Button) itemView.findViewById(R.id.join_btn);
            update = (ImageView) itemView.findViewById(R.id.update);
            delete = (ImageView) itemView.findViewById(R.id.delete);

            if (viewType == 1){
                showCourse = (Button) itemView.findViewById(R.id.viewCourse_btn);
                left = (Button) itemView.findViewById(R.id.leftCourse_btn);
            }

        }

    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder mainHolder, @SuppressLint("RecyclerView") int position) {
        mViewHolder holder = (mViewHolder) mainHolder;
        int viewType = mContentList.get(position).getIsJoined();
        final CourseInfo model = mContentList.get(position);


        String img = model.getCourseimage();

        // this code to display images
        if (img != null && !img.isEmpty()) {
            Glide.with(mContext).load(img).fitCenter().into(holder.courseImage);
            model.setCourseimage(img);
        }

        holder.courseName.setText(model.getCourseName());

        holder.courseType.setText(model.getCourseType());
        model.setCourseId(model.getCourseName());



        if (viewType == 0) {
            Button joinBtn = holder.join;
            //  holder.join.setVisibility(joinBtn.clic);
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference mycoursesRef = FirebaseDatabase.getInstance().getReference().child("mycourses");
                    DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
                    mycoursesRef.addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long coursesCount = snapshot.getChildrenCount();

                            if (coursesCount < 5) {
                                joinBtn.setText("added");
                                String id = mContentList.get(position).getCourseId();
                                String name = mContentList.get(position).getCourseName();
                                String image = mContentList.get(position).getCourseimage();
                                String type = mContentList.get(position).getCourseType();
                                int join = 1;

                                CourseInfo courseInfo = new CourseInfo(image, name, type, id, join);

                                courseInfo.setCourseimage(image);
                                courseInfo.setCourseName(name);
                                courseInfo.setCourseType(type);
                                courseInfo.setIsJoined(join);

                                mycoursesRef.child(id).setValue(courseInfo);

                                Toast.makeText(mContext, "MyCourse Added Successfully!", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(mContext, "You have exceeded the limit of courses!", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError error) {

                        }
                    });
                }
            });


//
            if (viewType == 1) {
                Button leftbtn = holder.left;
                leftbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        String id = mContentList.get(position).getCourseId();
                        builder.setTitle("Delete Course")
                                .setMessage("Are you sure, you want to left this course ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                        databaseReference = firebaseDatabase.getReference("mycourses").child(id);
                                        databaseReference.removeValue();
                                        Toast.makeText(mContext, "You left course!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(mContext, "Error ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }}}


    @Override
    public int getItemCount() {
        return mContentList.size();
    }


    }

