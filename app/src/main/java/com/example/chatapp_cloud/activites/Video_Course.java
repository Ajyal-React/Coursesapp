package com.example.chatapp_cloud.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp_cloud.R;
import com.example.chatapp_cloud.databinding.ActivityVideoCourseBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class Video_Course extends AppCompatActivity {
    String videolink1 = "https://firebasestorage.googleapis.com/v0/b/tutorex-87d31.appspot.com/o/Android%2F%D9%81%D9%8A%D8%AF%D9%8A%D9%88%20%230%20-%20%D8%AA%D8%BA%D9%8A%D9%8A%D8%B1%D8%A7%D8%AA%20%D9%81%D9%8A%20%D8%A8%D8%B1%D9%86%D8%A7%D9%85%D8%AC%20Android%20studio.mp4?alt=media&token=ddba5584-6d7b-4d13-964b-ff6a9254bea6";

    ActivityVideoCourseBinding binding;
    PlayerView pv1;
    SimpleExoPlayer player1;
    boolean playerwhenread= true;
    long currentposition= 0;
    int currentwindow= 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_course);
        binding = ActivityVideoCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pv1 = binding.exoplayerView1;
      binding.btnassm1Docx.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(),UploadWord.class);
              startActivity(intent);
          }
      });
        binding.btnassm1Pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UploadBDF.class);
                startActivity(intent);
            }
        });
//        binding.btnassm2Docx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),UploadWord.class);
//                startActivity(intent);
//            }
//        });
//        binding.btnassm2Pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),UploadBDF.class);
//                startActivity(intent);
//            }
//        });

    }

    private void initplayer(){
        player1 = new SimpleExoPlayer.Builder(this).build();
        pv1.setPlayer(player1);
//        player2 = new SimpleExoPlayer.Builder(this).build();
//        pv2.setPlayer(player2);
        MediaItem item1=  MediaItem.fromUri(videolink1);
//        MediaItem item2=  MediaItem.fromUri(videolink2);

        player1.setMediaItem(item1);
//        player2.setMediaItem(item2);

        player1.setPlayWhenReady(playerwhenread);
        player1.seekTo(currentwindow,currentposition);
//        player2.setPlayWhenReady(playerwhenread);
//        player2.seekTo(currentwindow,currentposition);
        player1.prepare();
//        player2.prepare();

    }
    private void releaseplayer(){
        if(player1 != null){
            playerwhenread = player1.getPlayWhenReady();
            currentwindow = player1.getCurrentWindowIndex();
            currentposition = player1.getCurrentPosition();
            player1 = null;
        }
//        if(player2 != null){
//            playerwhenread2 = player2.getPlayWhenReady();
//            currentwindow2 = player2.getCurrentWindowIndex();
//            currentposition2 = player2.getCurrentPosition();
//            player2 = null;
//        }
//        else {
//            playerwhenread2 = true;
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        initplayer();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(player1 != null){
            initplayer();

        }
//        if(player2 != null){
//            initplayer();
//
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseplayer();
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseplayer();
    }
}
