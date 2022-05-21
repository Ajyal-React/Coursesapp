package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.databinding.ActivitySignUpBinding;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private prefernceManager prefernceManager;
    private String encodedImage;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivitySignUpBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        prefernceManager = new prefernceManager( getApplicationContext() );
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
        setListeners();
    }

    private void setListeners() {
        binding.textsignIn.setOnClickListener( v -> onBackPressed() );
        binding.buttonSignup.setOnClickListener( v -> {

            if (isValidsignUpDetails()) {
                perforAuth();
            }
        } );
        binding.layoutImage.setOnClickListener( v -> {
            Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            pickImage.launch( intent );
        } );
    }

    private void showToast(String message) {
        Toast.makeText( getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
    }

    private void signUp() {
        loading(true);
        mFirestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME, binding.username.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.inputemail.getText().toString());
        user.put(Constants.KEY_password, binding.inputpassword.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
//        user.put(Constants.KEY_MIDDLE_NAME, binding.Middlename.getText().toString());
//        user.put(Constants.KEY_FAMILY_NAME, binding.familyname.getText().toString());
//        user.put(Constants.KEY_ADDRESS, binding.address.getText().toString());
        user.put(Constants.MOBILE_NUMBER, binding.mobilenum.getText().toString());

//        String email = binding.inputemail.getText().toString().trim();
//        String pasword = binding.inputpassword.getText().toString().trim();
//        mAuth.createUserWithEmailAndPassword(email,pasword).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_LONG).show();
//        mFirestore.collection(Constants.KEY_COLLECTION_USERS)
//                .add(user)
//                .addOnSuccessListener(documentReference -> {
//                    loading(false);
//                    prefernceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
//                    prefernceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
//                    prefernceManager.putString(Constants.KEY_FIRST_NAME, binding.firstname.getText().toString());
//                    prefernceManager.putString(Constants.KEY_IMAGE, encodedImage);
//                    prefernceManager.putString(Constants.KEY_MIDDLE_NAME, binding.Middlename.getText().toString());
//                    prefernceManager.putString(Constants.KEY_FAMILY_NAME, binding.familyname.getText().toString());
//                    prefernceManager.putString(Constants.KEY_ADDRESS, binding.address.getText().toString());
//                    prefernceManager.putString(Constants.MOBILE_NUMBER, binding.mobilenum.getText().toString());
//                    Intent intent = new Intent(getApplicationContext(), ChatmainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                })
//                .addOnFailureListener(exception -> {
//                    loading(false);
//                    showToast(exception.getMessage());
//                });
//        // }
//            }else {
//                Toast.makeText(SignUpActivity.this,"Error"+task.getException().getMessage(),Toast.LENGTH_LONG);
////                binding.progressBar.setVisibility(View.GONE);
//            }

//        });

//        mFirestore.collection( Constants.KEY_COLLECTION_USERS )
//                .add( user )
//                .addOnSuccessListener( documentReference -> {
//                    loading( false );
//                    prefernceManager.putBoolean( Constants.KEY_IS_SIGNED_IN, true );
//                    prefernceManager.putString( Constants.KEY_USER_ID, documentReference.getId() );
//                    prefernceManager.putString( Constants.KEY_FIRST_NAME, binding.firstname.getText().toString() );
//                    prefernceManager.putString( Constants.KEY_IMAGE, encodedImage );
//                    prefernceManager.putString(Constants.KEY_MIDDLE_NAME,binding.Middlename.getText().toString());
//                    prefernceManager.putString(Constants.KEY_FAMILY_NAME,binding.familyname.getText().toString());
//                    prefernceManager.putString(Constants.KEY_ADDRESS,binding.address.getText().toString());
//                    prefernceManager.putString(Constants.MOBILE_NUMBER,binding.mobilenum.getText().toString());
//                    Intent intent = new Intent( getApplicationContext(), ChatmainActivity.class );
//                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( intent );
//                } )
//                .addOnFailureListener( exception -> {
//                    loading( false );
//                    showToast( exception.getMessage() );
//                } );
    }
    private void perforAuth() {
        Constants.KEY_password = binding.inputpassword.getText().toString();
        Constants.KEY_EMAIL =binding.inputemail.getText().toString();
//        Constants.KEY_ADDRESS = binding.address.getText().toString();
        Constants.KEY_FIRST_NAME = binding.username.getText().toString();
//        Constants.KEY_FAMILY_NAME = binding.familyname.getText().toString();
//        Constants.KEY_MIDDLE_NAME = binding.Middlename.getText().toString();


        // Constants.KEY_IMAGE = binding.imageprofile.getImageBitmap();

        loading(true);
        mFirestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME, binding.username.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.inputemail.getText().toString());
        user.put(Constants.KEY_password, binding.inputpassword.getText().toString());
        user.put(Constants.KEY_IMAGE, encodedImage);
//        user.put(Constants.KEY_MIDDLE_NAME, binding.Middlename.getText().toString());
//        user.put(Constants.KEY_FAMILY_NAME, binding.familyname.getText().toString());
//        user.put(Constants.KEY_ADDRESS, binding.address.getText().toString());
        user.put(Constants.MOBILE_NUMBER, binding.mobilenum.getText().toString());

        mAuth.createUserWithEmailAndPassword( Constants.KEY_password,  Constants.KEY_EMAIL)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
            public void onComplete(@NonNull Task<AuthResult> v) {

                    if (v.isSuccessful()){

                     //   loading(true);
//                FirebaseFirestore database = FirebaseFirestore.getInstance();
                        mFirestore.collection(Constants.KEY_COLLECTION_USERS)
                                .add(user)
                                .addOnSuccessListener(documentReference ->

                                {
                                    loading(false);
                                    prefernceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                    prefernceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                                    prefernceManager.putString(Constants.KEY_FIRST_NAME, binding.username.getText().toString());
                                    prefernceManager.putString(Constants.KEY_IMAGE, encodedImage);
//                                    prefernceManager.putString(Constants.KEY_MIDDLE_NAME, binding.Middlename.getText().toString());
//                                    prefernceManager.putString(Constants.KEY_FAMILY_NAME, binding.familyname.getText().toString());
//                                    prefernceManager.putString(Constants.KEY_ADDRESS, binding.address.getText().toString());
                                    prefernceManager.putString(Constants.MOBILE_NUMBER, binding.mobilenum.getText().toString());
                                }
                                )
                .addOnFailureListener(exception -> {
                                loading(false);
                                showToast(exception.getMessage());
                            });
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChatmainActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_LONG);
                    }

//         }
            }
        });



    }
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap( bitmap, previewWidth, previewHeight, false );
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress( Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream );
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString( bytes, Base64.DEFAULT );
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream( imageUri );
                            Bitmap bitmap = BitmapFactory.decodeStream( inputStream );
                            binding.imageprofile.setImageBitmap( bitmap );
                            binding.textAddImage.setVisibility( View.GONE );
                            encodedImage = encodeImage( bitmap );
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private Boolean isValidsignUpDetails() {
        if (encodedImage == null) {
            showToast( "select profile image" );
            return false;
        } else if (binding.username.getText().toString().trim().isEmpty()) {
            showToast( "Enter First Name" );
            return false;
        }

        else if (binding.mobilenum.getText().toString().trim().isEmpty()) {
            showToast( "Enter Address" );
            return false;
        }
        else if (binding.brithdate.getText().toString().trim().isEmpty()) {
            showToast( "Enter Brithdate" );
            return false;
        }
        else if (binding.inputemail.getText().toString().trim().isEmpty()) {
            showToast( "Enter email" );
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( binding.inputemail.getText().toString() ).matches()) {
            showToast( "Enter valid email" );
            return false;
        } else if (binding.inputpassword.getText().toString().trim().isEmpty()) {
            showToast( "Enter password" );
            return false;
        } else if (binding.inputpassword.getText().toString().trim().isEmpty()) {
            showToast( "confirm your password" );
            return false;
        }
         else {
            return true;
        }
    }


    private  void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignup.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.buttonSignup.setVisibility(View.VISIBLE);
        }
    }

}