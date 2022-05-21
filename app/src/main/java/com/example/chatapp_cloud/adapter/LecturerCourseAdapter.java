package com.example.chatapp_cloud.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.models.CourseInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LecturerCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private ArrayList<CourseInfo> contentList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
//    MyFirebaseMessagingService service;
//    MyFirebaseInstanceIDService serviceId;

    public LecturerCourseAdapter(Context mContext, ArrayList<CourseInfo> mContentList) {
        this.context = mContext;
        this.contentList = mContentList;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.lect_courses_layout, parent, false);
        return new ViewHolder(view1);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseName;
        private  TextView courseType;
        private  ImageView courseImage;
        ImageView update, delete;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.coursename);
            courseType = (TextView) itemView.findViewById(R.id.coursetype);
            courseImage = (ImageView) itemView.findViewById(R.id.courseimage);
            update = (ImageView) itemView.findViewById(R.id.update);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewHolder lectHolder = (ViewHolder) holder;
        //   int viewType = contentList.get(position).getIsJoined();
        final CourseInfo model = contentList.get(position);

        ImageView updateCourse = lectHolder.update;
        ImageView deleteCourse = lectHolder.delete;
        String img = model.getCourseimage();

        if (img != null && !img.isEmpty()) {
            Glide.with(context).load(img).fitCenter().into(lectHolder.courseImage);
            model.setCourseimage(img);
        }

        lectHolder.courseName.setText(model.getCourseName());

        lectHolder.courseType.setText(model.getCourseType());
        model.setCourseId(model.getCourseName());

        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String id = contentList.get(position).getCourseId();
                builder.setTitle("Delete Course")
                        .setMessage("Are you sure, you want to delete this course ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                databaseReference = firebaseDatabase.getReference("courses").child(id);
                                databaseReference.removeValue();
                                Toast.makeText(context, "Course Deleted Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context,"Course not deleted",Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return contentList.size();
    }

//    public void pushNotification(){
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,);
//        service = new MyFirebaseMessagingService();
//        service.startService(serviceId).
//    }
}

