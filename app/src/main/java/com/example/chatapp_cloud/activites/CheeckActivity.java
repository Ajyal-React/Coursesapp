package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CheeckActivity extends AppCompatActivity {
    EditText passcheeck;
    Button butonCheeck;
    String Stpass;
  //  private CollectionReference database;
    DocumentSnapshot documentSnapshot;
    FirebaseFirestore mydb;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheeck);
       passcheeck = findViewById(R.id.passcheeck);
       butonCheeck = findViewById(R.id.butonCheeck);
//       database=FirebaseFirestore.getInstance().collection("admin");
//       database.getId();
      Stpass= passcheeck.getText().toString();
//       butonCheeck.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//            // CollectionReference documentReference = mydb.collection("adin");
//               mydb.collection("admin").document("authcode");
//
//
//               DocumentReference docRef= (DocumentReference) mydb.document("authcode");
//               docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                   @Override
//                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                       if (task.isSuccessful()){
//                           DocumentSnapshot documant=task.getResult();
//                           if (documant!= null){
//                               String value = docRef.getId();
//                 if (Stpass.equals(value)){
//                     startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
//                               }else {
//                     Toast.makeText(CheeckActivity.this, "Error in Cheeck code", Toast.LENGTH_SHORT).show();
//                 }
//                           }else{
//                               Log.d("LOGER","get felied ");
//                           }
//                       }
//                   }
//               });
//           }
//       });
        DocumentReference docRef = mydb.collection("admin").document("authcode");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot documant = task.getResult();
                   if (documant != null) {
                       String value = docRef.getId();
                       if (Stpass.equals(value)) {
                           startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                       } else {
                           Toast.makeText(CheeckActivity.this, "Error in Cheeck code", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Log.d("LOGER", "get felied ");
                   }
               }
           }
      });

//        mydb.collection("admin")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

    }
}