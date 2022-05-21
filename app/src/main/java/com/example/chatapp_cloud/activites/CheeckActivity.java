package com.example.chatapp_cloud.activites;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CheeckActivity extends AppCompatActivity {
    EditText passcheeck;
    Button butonCheeck;
    String Stpass;
  //  private CollectionReference database;
    DocumentSnapshot documentSnapshot;
    FirebaseFirestore firestore;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheeck);
       passcheeck = findViewById(R.id.passcheeck);
       butonCheeck = findViewById(R.id.butonCheeck);
      Stpass= passcheeck.getText().toString();
     // String pass= "4444";
//      firebaseAuth = FirebaseAuth.getInstance();
      firestore = FirebaseFirestore.getInstance();
      CollectionReference db= firestore.collection("admins");
      String code = FirebaseFirestore.getInstance()
              .collection("admins")
              .whereEqualTo("code","4444")
              .get().toString();
      //userid = firebaseAuth.getCurrentUser().getUid();
        butonCheeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Stpass.equals(code)){
                    FirebaseFirestore.getInstance()
                            .collection("admins")
                            .whereEqualTo("code","4444")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    Log.d("tt", "we are geeting data");
                                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot snapshot : snapshots) {
                                        Log.d("tt", "onSuuceful" + snapshot.getData().toString());
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CheeckActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

//            }
    });
    }}


//FirebaseFirestore.getInstance()
//        .collection("admins")
//        .get()
//        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//@Override
//public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//        Log.d("tt", "we are geeting data");
//        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
//        for (DocumentSnapshot snapshot : snapshots) {
//        Log.d("tt", "onSuuceful" + snapshot.getData().toString());
//        }
//
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(CheeckActivity.this, "Error", Toast.LENGTH_SHORT).show();
//        }
//        });