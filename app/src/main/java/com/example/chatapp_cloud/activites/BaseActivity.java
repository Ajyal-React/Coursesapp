package com.example.chatapp_cloud.activites;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseActivity extends AppCompatActivity {
    private DocumentReference documentReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        prefernceManager prefernceManager = new prefernceManager( getApplicationContext() );
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        documentReference = database.collection( Constants.KEY_COLLECTION_USERS )
                .document(prefernceManager.getString( Constants.KEY_USER_ID ));
    }
    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update( Constants.KEY_AVATIABTLITY,0 );
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update( Constants.KEY_AVATIABTLITY,1 );
    }
}
