package com.alexllanas.openaudio.presentation.main.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.alexllanas.core.util.Constants.Companion.CHANNEL_ID
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.NotificationBroadcastReceiver
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerState
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.offline.DownloadService.startForeground
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by viewModels()
    lateinit var notificationManager: NotificationManagerCompat
    var isPlaying = false
    private var videoId = ""

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ytView = findViewById<YouTubePlayerView>(R.id.youtube_view)
        ytView.enableBackgroundPlayback(true)
        var currentSecond = 0F

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.authFragment)

        val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                if (currentSecond != second) {
                    mediaPlayerViewModel.dispatch(HomeAction.SetCurrentSecond(second))
                    currentSecond = second
                }
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                Log.d(TAG, "onVideoDuration: $duration")
                mediaPlayerViewModel.dispatch(HomeAction.SetDuration(duration = duration))
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                mediaPlayerViewModel.dispatch(HomeAction.SetPlaybackState(state))
            }
        }

        notificationManager = NotificationManagerCompat.from(this)

        val intentFilter = IntentFilter("com.alexllanas.openaudio.TOGGLE_PLAYBACK")
        this.registerReceiver(
            NotificationBroadcastReceiver(mediaPlayerViewModel),
            intentFilter
        )
        val tracker = YouTubePlayerTracker()
        mediaPlayerViewModel.dispatch(HomeAction.SetMediaTracker(tracker))
        lifecycleScope.launch(Dispatchers.IO) {
            mediaPlayerViewModel.mediaPlayerState.collect { state ->
                showNotification(state, notificationManager)
                state.currentPlayingTrack?.let { _ ->
                    state.videoId.let {
                        if (videoId != it) {
                            ytView.getYouTubePlayerWhenReady(object :
                                YouTubePlayerCallback {
                                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                                    youTubePlayer.addListener(tracker)
                                    youTubePlayer.addListener(youTubePlayerListener)
                                    if (it.startsWith("/yt/")) {
                                        mediaPlayerViewModel.dispatch(
                                            HomeAction.SetYoutubePlayer(
                                                youTubePlayer
                                            )
                                        )
                                        youTubePlayer.loadVideo(it.removePrefix("/yt/"), 0f)
                                        isPlaying = true
                                        mediaPlayerViewModel.dispatch(
                                            HomeAction.SetIsPlaying(
                                                isPlaying
                                            )
                                        )
                                    }
                                }
                            })
                            videoId = it
                        }
                    }
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNotification(
        mediaPlayerState: MediaPlayerState,
        notificationManager: NotificationManagerCompat
    ) {
        var bitmap: Bitmap? = null
//        mediaPlayerState.currentPlayingTrack?.image?.let { mediaUrl ->
//            val url = URL(mediaUrl)
//            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//        }
        try {
            bitmap = Glide.with(this)
                .asBitmap()
                .load(mediaPlayerState.currentPlayingTrack?.image)
                .submit()
                .get()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val playbackIntent = Intent()
        playbackIntent.action = "com.alexllanas.openaudio.TOGGLE_PLAYBACK"
        val playbackPendingIntent =
            PendingIntent.getBroadcast(this, 0, playbackIntent, PendingIntent.FLAG_IMMUTABLE)

        val launchIntent = Intent(this, MainActivity::class.java)
        launchIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val launchPendingIntent =
            PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .addAction(
//                    com.google.android.exoplayer2.ui.R.drawable.exo_controls_previous,
//                    "Previous",
//                    playbackPendingIntent
//                )
                .addAction(
                    com.google.android.exoplayer2.ui.R.drawable.exo_controls_pause,
                    "Pause",
                    playbackPendingIntent
                )
//                .addAction(
//                    com.google.android.exoplayer2.ui.R.drawable.exo_controls_next,
//                    "Next",
//                    playbackPendingIntent
//                )
                .setContentIntent(launchPendingIntent)
                .setContentText(
                    mediaPlayerState.currentPlayingTrack?.title ?: "no title"
                )
                .setSilent(true)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0)
                )
//                .setStyle(
//                    NotificationCompat
//                        .BigPictureStyle()
//                        .bigLargeIcon(bitmap)
//                        .showBigPictureWhenCollapsed(true)
//                )
//                .setLargeIcon(bitmap)
                .build()
        } else {
            TODO("VERSION.SDK_INT < S")
        }

        if (mediaPlayerState.playbackState == PlayerConstants.PlayerState.PLAYING
//            || mediaPlayerState.playbackState == PlayerConstants.PlayerState.BUFFERING
        ) {
            notification.actions[0] = Notification.Action(
                com.google.android.exoplayer2.ui.R.drawable.exo_controls_pause,
                "pause",
                playbackPendingIntent
            )
        } else {
            notification.actions[0] = Notification.Action(
                com.google.android.exoplayer2.ui.R.drawable.exo_controls_play,
                "play",
                playbackPendingIntent
            )
        }
        notificationManager.notify(1, notification)
    }

//    override fun onBackPressed() {
//        moveTaskToBack(false)
//    }

}
