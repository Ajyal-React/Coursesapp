package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.databinding.ActivitySiginBinding;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SiginActivity extends AppCompatActivity {
    private ActivitySiginBinding binding;
    private prefernceManager prefernceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefernceManager = new prefernceManager(getApplicationContext());
        if(prefernceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), ChatmainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySiginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        binding.buttonSignIn.setOnClickListener(v ->{
            if(isValidSignInDetails()){
                signIn();
            }
        });
    }
    private void signIn(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,binding.inputemail.getText().toString())
                .whereEqualTo(Constants.KEY_password, binding.inputpassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()&& task.getResult() != null
                            && task.getResult().getDocuments().size() >0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        prefernceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        prefernceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        prefernceManager.putString(Constants.KEY_FIRST_NAME,documentSnapshot.getString(Constants.KEY_FIRST_NAME));
                        prefernceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(), ChatmainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        loading(false);
                        showToast("unable to sign in");
                    }
                });
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignIn.setVisibility(View.VISIBLE);
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private Boolean isValidSignInDetails(){
        if(binding.inputemail.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputemail.getText().toString()).matches()){
            showToast("Enter valid Email");
            return false;
        }else if(binding.inputpassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }else {
            return true;
        }
    }
}
