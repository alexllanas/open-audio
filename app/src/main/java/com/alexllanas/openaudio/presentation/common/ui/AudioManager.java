package com.alexllanas.openaudio.presentation.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;


public class AudioManager {

    void play(Context context, ExoPlayer player, Boolean playWhenReady, int currentWindow, int playbackPosition) {


    }
//        new YouTubeExtractor(context) {
//
//            @SuppressLint("StaticFieldLeak")
//            @Override
//            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
//                if (ytFiles != null) {
//                    int videoTag = 137;
//                    int audioTag = 140;
//                    MediaSource audioSource = new ProgressiveMediaSource.Factory(
//                            new DefaultHttpDataSource.Factory()
//                    )
//                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));
//                    MediaSource videoSource = new ProgressiveMediaSource.Factory(
//                            new DefaultHttpDataSource.Factory()
//                    )
//                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).getUrl()));
//                    player.setMediaSource(new MergingMediaSource(
//                            true,
//                            videoSource,
//                            audioSource
//                    ));
//                    player.prepare();
//                    player.setPlayWhenReady(playWhenReady);
//                    player.seekTo(currentWindow, playbackPosition);
//                }
//            }
//        }.extract("https://www.youtube.com/watch?v=xo4lRv51Hf0&ab_channel=IzaeldeMouraPereira&html5=1", false, true);
//    }
}
