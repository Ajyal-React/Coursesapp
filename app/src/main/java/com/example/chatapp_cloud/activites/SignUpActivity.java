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
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.databinding.ActivitySignUpBinding;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
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
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivitySignUpBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        prefernceManager = new prefernceManager( getApplicationContext() );
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setListeners();
    }

    private void setListeners() {
        binding.textsignIn.setOnClickListener( v -> onBackPressed() );
        binding.buttonSignup.setOnClickListener( v -> {
            if (isValidsignUpDetails()) {
                signUp();
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
        mFirestore = FirebaseFirestore.getInstance();
        loading( true );
        HashMap<String, Object> user = new HashMap<>();
        user.put( Constants.KEY_FIRST_NAME, binding.firstname.getText().toString() );
        user.put( Constants.KEY_EMAIL, binding.inputemail.getText().toString() );
        user.put( Constants.KEY_password, binding.inputpassword.getText().toString() );
        user.put( Constants.KEY_IMAGE, encodedImage );
        mFirestore.collection( Constants.KEY_COLLECTION_USERS )
                .add( user )
                .addOnSuccessListener( documentReference -> {
                    loading( false );
                    prefernceManager.putBoolean( Constants.KEY_IS_SIGNED_IN, true );
                    prefernceManager.putString( Constants.KEY_USER_ID, documentReference.getId() );
                    prefernceManager.putString( Constants.KEY_FIRST_NAME, binding.firstname.getText().toString() );
                    prefernceManager.putString( Constants.KEY_IMAGE, encodedImage );
                    Intent intent = new Intent( getApplicationContext(), ChatmainActivity.class );
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity( intent );

                } )
                .addOnFailureListener( exception -> {
                    loading( false );
                    showToast( exception.getMessage() );
                } );
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
        } else if (binding.firstname.getText().toString().trim().isEmpty()) {
            showToast( "Enter First Name" );
            return false;
        } else if (binding.Middlename.getText().toString().trim().isEmpty()) {
            showToast( "Enter middle Name" );
            return false;
        } else if (binding.familyname.getText().toString().trim().isEmpty()) {
            showToast( "Enter Family Name" );
            return false;
        } else if (binding.address.getText().toString().trim().isEmpty()) {
            showToast( "Enter Address" );
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
        } else if (binding.inputconfirmpassword.getText().toString().trim().isEmpty()) {
            showToast( "confirm your password" );
            return false;
        } else if (!binding.inputpassword.getText().toString().equals( binding.inputconfirmpassword.getText().toString() )) {
            showToast( "password & confirm password must be same" );
            return false;
        } else {
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