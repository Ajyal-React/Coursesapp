package com.example.chatapp_cloud.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.databinding.ItemContainerUserBinding;
import com.example.chatapp_cloud.listeners.StudentListener;
import com.example.chatapp_cloud.models.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder> {
    private final List<Student> students;
    private final StudentListener studentListener;
    public StudentAdapter(List<Student> students,StudentListener studentListener) {
        this.studentListener=studentListener;
        this.students = students;
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from( parent.getContext() ),
                parent,
                false
        );
        return new  studentViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.studentViewHolder holder, int position) {
        holder.setUserData( students.get( position ) );
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class studentViewHolder extends RecyclerView.ViewHolder{
        ItemContainerUserBinding binding;
        studentViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }
        void setUserData(Student student){
            binding.textName.setText(student.Firstname);
            binding.textName.setText(student.Middlename);
            binding.textName.setText(student.Familyname);
            binding.textName.setText(student.MobileNum);
            binding.textName.setText(student.Address);
            binding.textEmail.setText(student.email );
            binding.imageprofile.setImageBitmap(getUserImage( student.image ));
            binding.getRoot().setOnClickListener( v -> studentListener.onStudentClicked(student) );
        }
    }
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
