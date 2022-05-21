package com.example.chatapp_cloud.activites;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResgisterActivity extends AppCompatActivity {
    private EditText email, password;
    private Button Btn;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwd);
        progressDialog = new ProgressDialog(this);
        Btn = findViewById(R.id.btnregister);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              perforAuth();
            }
        });

    }

    private void perforAuth() {
        String pass = password.getText().toString();
        String ema =email.getText().toString();
        if (password==null||password.length()<6){
            password.setError("Enter proper password");
        }else {
            progressDialog.setMessage("plase Wait while Resister ");
            progressDialog.setTitle("Resgister");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(ema,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(ResgisterActivity.this, "Resgister successful", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(ResgisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(ResgisterActivity.this,Truset.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
