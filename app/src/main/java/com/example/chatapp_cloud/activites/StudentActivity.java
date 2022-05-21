package com.example.chatapp_cloud.activites;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatapp_cloud.adapter.StudentAdapter;
import com.example.chatapp_cloud.databinding.ActivityStudentBinding;
import com.example.chatapp_cloud.listeners.StudentListener;
import com.example.chatapp_cloud.models.Student;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends BaseActivity implements StudentListener {
    private ActivityStudentBinding binding;
    private prefernceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding =ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );
        preferenceManager = new prefernceManager( getApplicationContext() );
        setListeners();
        getUsers();
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility( View.VISIBLE );
        }else{
            binding.progressBar.setVisibility( View.INVISIBLE );
        }
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener( v -> onBackPressed() );
    }
    private void getUsers(){
        loading( true );
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection( Constants.KEY_COLLECTION_USERS )
                .get()
                .addOnCompleteListener( task -> {
                    loading( false );
                    String currentUserId = preferenceManager.getString( Constants.KEY_USER_ID );
                    if(task.isSuccessful() && task.getResult() != null){
                        List<Student> students = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if(currentUserId.equals( queryDocumentSnapshot.getId() )){
                                continue;
                            }
                            Student student = new Student();
                            student.Firstname= queryDocumentSnapshot.getString( Constants.KEY_FIRST_NAME );
                            student.Middlename=queryDocumentSnapshot.getString( Constants.KEY_MIDDLE_NAME );
                            student.Familyname=queryDocumentSnapshot.getString( Constants.KEY_FAMILY_NAME );
                            student.Address=queryDocumentSnapshot.getString( Constants.KEY_ADDRESS );
                            student.MobileNum=queryDocumentSnapshot.getString( Constants.MOBILE_NUMBER );
                            student.Middlename=queryDocumentSnapshot.getString( Constants.KEY_BIRTHDATE );
                            student.email = queryDocumentSnapshot.getString( Constants.KEY_EMAIL );
                            student.image = queryDocumentSnapshot.getString( Constants.KEY_IMAGE );
                            student.token = queryDocumentSnapshot.getString( Constants.KEY_FCM_TOKEN);
                            student.id = queryDocumentSnapshot.getId();
                            students.add( student );
                        }
                        if(students.size() >0){
                            StudentAdapter studentAdapter = new StudentAdapter( students ,this );
                            binding.studentRecycleView.setAdapter( studentAdapter );
                            binding.studentRecycleView.setVisibility( View.VISIBLE );
                        }else {
                            showErrorMessage();
                        }
                    }else {
                        showErrorMessage();
                    }
                } );
    }
    private void showErrorMessage(){
        binding.textErrorMess.setText( String.format( "%s","No user available" ) );
        binding.textErrorMess.setVisibility( View.VISIBLE );
    }

    @Override
    public void onStudentClicked(Student student) {
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra( Constants.KEY_STUDENT,student );
        startActivity( intent );
        finish();

    }
}