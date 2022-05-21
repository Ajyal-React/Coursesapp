package com.example.chatapp_cloud.activites;

import static com.example.chatapp_cloud.utilites.Constants.KEY_BIRTHDATE;
import static com.example.chatapp_cloud.utilites.Constants.KEY_EMAIL;
import static com.example.chatapp_cloud.utilites.Constants.KEY_FIRST_NAME;
import static com.example.chatapp_cloud.utilites.Constants.KEY_password;
import static com.example.chatapp_cloud.utilites.Constants.MOBILE_NUMBER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.databinding.ActivitySignUpBinding;
import com.example.chatapp_cloud.models.Student;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private prefernceManager prefernceManager;
    private String encodedImage;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RadioButton lecturer, student;
    Bitmap userImage;
    EditText userName, userEmail, userBirth, userPass, userMobile ;
    String name, memail, pass, mobile ,birth;
    String imagere;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivitySignUpBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        prefernceManager = new prefernceManager( getApplicationContext() );
        setListeners();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RGroup);

        userName = findViewById(R.id.username);
        userBirth = findViewById(R.id.brithdate);
        userMobile = findViewById(R.id.mobilenum);
        userEmail = findViewById(R.id.inputemail);
        userPass = findViewById(R.id.inputpassword);
        lecturer = findViewById(R.id.radio1);
        student = findViewById(R.id.radio2);
        registerBtn = findViewById(R.id.buttonSignup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.radio1) {
                    registerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registerLecturer();
                        }
                    });

                } if (id == R.id.radio2) {
                    registerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registerStudent();

                        }
                    });

                }
            }
        });
    }

    private void setListeners() {
        binding.textsignIn.setOnClickListener( v -> onBackPressed() );

        binding.layoutImage.setOnClickListener( v -> {
            Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            pickImage.launch( intent );
        } );
    }

    private void showToast(String message) {
        Toast.makeText( getApplicationContext(), message, Toast.LENGTH_SHORT ).show();
    }

    //    private void signUp() {
//        loading( true );
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        HashMap<String, Object> user = new HashMap<>();
//        user.put( Constants.KEY_FIRST_NAME, binding.username.getText().toString() );
//        user.put( Constants.KEY_EMAIL, binding.inputemail.getText().toString() );
//        user.put( Constants.KEY_password, binding.inputpassword.getText().toString() );
//        user.put( Constants.KEY_IMAGE, encodedImage );
//        database.collection( Constants.KEY_COLLECTION_USERS )
//                .add( user )
//                .addOnSuccessListener( documentReference -> {
//                    loading( false );
//                    prefernceManager.putBoolean( Constants.KEY_IS_SIGNED_IN, true );
//                    prefernceManager.putString( Constants.KEY_USER_ID, documentReference.getId() );
//                    prefernceManager.putString( Constants.KEY_FIRST_NAME, binding.username.getText().toString() );
//                    prefernceManager.putString( Constants.KEY_IMAGE, encodedImage );
//                    Intent intent = new Intent( getApplicationContext(), MainChating.class );
//                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( intent );
//
//                } )
//                .addOnFailureListener( exception -> {
//                    loading( false );
//                    showToast( exception.getMessage() );
//                } );
//    }
    private void addDataStudent(String name , String birth , String mobile , String image , String email) {
        CollectionReference dbRef = firebaseFirestore.collection("students");
        HashMap<String, Object> user = new HashMap<>();

        Student model = new Student(name,birth,mobile,image,email);
        dbRef.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                user.put( name, binding.username.getText().toString() );
                user.put( memail, binding.inputemail.getText().toString() );
                user.put( birth, binding.brithdate.getText().toString() );
                user.put( pass, binding.inputpassword.getText().toString() );
                user.put( imagere, encodedImage );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void addDataLecturer(String email,String name , String birth ,String image, String mobile  ) {
        CollectionReference dbRef = firebaseFirestore.collection("students");
        HashMap<String, Object> user = new HashMap<>();

        Student model = new Student(email,name,birth,image,mobile);
        dbRef.add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                user.put( name, binding.username.getText().toString() );
                user.put( memail, binding.inputemail.getText().toString() );
                user.put( birth, binding.brithdate.getText().toString() );
                user.put( pass, binding.inputpassword.getText().toString() );
                user.put( imagere, encodedImage );
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

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
            showToast("Enter First Name");
            return false;

        }  else if (binding.mobilenum.getText().toString().trim().isEmpty()) {
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
            showToast("Enter password");
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
    private void registerLecturer() {
        memail = userEmail.getText().toString();
        pass = userPass.getText().toString();
        birth = userBirth.getText().toString();
        mobile = userMobile.getText().toString();
        name = userName.getText().toString();
        // imagere = userImage.;

        memail = KEY_EMAIL;
        pass = KEY_password;
        birth = KEY_BIRTHDATE;
        name = KEY_FIRST_NAME;
        mobile = MOBILE_NUMBER;

        if (TextUtils.isEmpty(memail)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(birth)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(memail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            addDataLecturer(memail, name, birth, imagere, mobile);
                            startActivity(new Intent(SignUpActivity.this, HomePage.class));

                        }
                        Toast.makeText(SignUpActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void registerStudent(){

        memail = userEmail.getText().toString();
        pass = userPass.getText().toString();
        birth = userBirth.getText().toString();
        mobile = userMobile.getText().toString();
        name = userName.getText().toString();

        if (TextUtils.isEmpty(memail)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(birth)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(memail,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            addDataStudent(name,birth,mobile,imagere,memail);
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        }
                    }
                });
    }

}