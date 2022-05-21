package com.example.chatapp_cloud.activites;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    SimpleExoPlayer exoPlayer;
    private PlayerView playerView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
    }
    public void setVideo(final Application ctx,String title,final String url,DataSource.Factory dataSourceFactory){
        TextView mtextView = mview.findViewById(R.id.titletv);
        playerView= mview.findViewById(R.id.exoplayer_view);
        mtextView.setText(title);
//        try {
//            BandwidthMeter bandwidthMeter= new DefaultBandwidthMeter.Builder(ctx).build();
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//        }catch ();
        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(ctx).build();
//            DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//        exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(ctx);
            Uri video = Uri.parse(url);
            DefaultHttpDataSource dataSource = new DefaultHttpDataSource("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(video,dataSource,extractorsFactory,null);
            playerView.setPlayer(exoPlayer);
//            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);

        } catch (Exception e) {
            Log.e("ViewHolder","exoplayer error" + e.toString());

        }
    }
}
