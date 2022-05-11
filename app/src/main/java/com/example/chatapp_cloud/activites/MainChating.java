package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;


import com.example.chatapp_cloud.adapter.RecentConversionsAdapter;
import com.example.chatapp_cloud.databinding.ActivityMainBinding;
import com.example.chatapp_cloud.listeners.ConversionListener;
import com.example.chatapp_cloud.models.ChatMessage;
import com.example.chatapp_cloud.models.Student;
import com.example.chatapp_cloud.utilites.Constants;
import com.example.chatapp_cloud.utilites.prefernceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainChating extends BaseActivity implements ConversionListener {
    public ActivityMainBinding binding ;
    private  prefernceManager prefernceManager;
    private List<ChatMessage> conversations;
    private RecentConversionsAdapter conversionsAdapter;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefernceManager = new prefernceManager(getApplicationContext());
        init();
        loadUserDetails();
        getToken();
        setListeners();
        listenConversations();

    }
    private void init(){
        conversations = new ArrayList<>();
        conversionsAdapter = new RecentConversionsAdapter( conversations , this );
        binding.conversationsRecyclerView.setAdapter( conversionsAdapter );
        database =FirebaseFirestore.getInstance();
    }
    private void setListeners(){
        binding.imageSignOut.setOnClickListener(v -> signOut());
        binding.fabNewChat.setOnClickListener( v ->
                startActivity( new Intent(getApplicationContext(), StudentActivity.class) ));
    }
    private void loadUserDetails(){
        binding.textName.setText(prefernceManager.getString(Constants.KEY_FIRST_NAME));
        byte[] bytes = Base64.decode(prefernceManager.getString(Constants.KEY_IMAGE),
                Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        binding.imageprofile.setImageBitmap(bitmap);
}
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private void listenConversations(){
        database.collection( Constants.KEY_COLLECTION_CONVERSATIONS )
                .whereEqualTo( Constants.KEY_SENDER_ID,prefernceManager.getString( Constants.KEY_USER_ID ) )
                .addSnapshotListener( eventListener );
        database.collection( Constants.KEY_COLLECTION_CONVERSATIONS )
                .whereEqualTo( Constants.KEY_RECEIVER_ID,prefernceManager.getString( Constants.KEY_USER_ID ) )
                .addSnapshotListener( eventListener );
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return;
        }
        if (value != null){
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString( Constants.KEY_SENDER_ID );
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receverId = receiverId;
                    if (prefernceManager.getString( Constants.KEY_USER_ID ).equals( senderId )){
                        chatMessage.conversionImage = documentChange.getDocument().getString( Constants.KEY_RECEIVER_IMAGE );
                        chatMessage.conversionName = documentChange.getDocument().getString( Constants.KEY_RECEIVER_NAME );
                        chatMessage.conversionId = documentChange.getDocument().getString( Constants.KEY_RECEIVER_ID );

                    }else {
                        chatMessage.conversionImage = documentChange.getDocument().getString( Constants.KEY_SENDER_IMAGE );
                        chatMessage.conversionName = documentChange.getDocument().getString( Constants.KEY_SENDER_NAME );
                        chatMessage.conversionId = documentChange.getDocument().getString( Constants.KEY_SENDER_ID );
                    }
                    chatMessage.message = documentChange.getDocument().getString( Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate( Constants.KEY_TIMESTAMP );
                    conversations.add( chatMessage );
                }else  if (documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for (int i=0 ;i<conversations.size();i++){
                        String senderId = documentChange.getDocument().getString( Constants.KEY_SENDER_ID );
                        String receiverId = documentChange.getDocument().getString( Constants.KEY_RECEIVER_ID );
                  if (conversations.get( i ).senderId.equals( senderId ) &&conversations.get( i ).receverId.equals( receiverId )){
                      conversations.get( i ).message = documentChange.getDocument().getString( Constants.KEY_LAST_MESSAGE );
                      conversations.get( i ).dateObject =documentChange.getDocument().getDate( Constants.KEY_TIMESTAMP );
                      break;
                  }
                    }
                }
            }
            Collections.sort( conversations,(obj1 , obj2) -> obj2.dateObject.compareTo( obj1.dateObject ));
            conversionsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition( 0 );
            binding.conversationsRecyclerView.setVisibility( View.VISIBLE );
            binding.ProgressBar.setVisibility( View.GONE );

        }

    };
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void updateToken(String token){
        prefernceManager.putString( Constants.KEY_FCM_TOKEN,token );
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        prefernceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN,token)
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }
    private void signOut(){
        showToast("Signing out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        prefernceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String,Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    prefernceManager.clear();
                    startActivity(new Intent(getApplicationContext(),SiginActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }

    @Override
    public void onConversionClicked(Student student) {
        Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra( Constants.KEY_STUDENT, String.valueOf( student ) );
        startActivity( intent );
    }
}