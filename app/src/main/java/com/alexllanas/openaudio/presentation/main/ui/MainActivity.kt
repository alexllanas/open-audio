package com.alexllanas.openaudio.presentation.main.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.alexllanas.core.util.Constants.Companion.CHANNEL_ID
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.NotificationBroadcastReceiver
import com.alexllanas.openaudio.presentation.auth.ui.AuthFragment
import com.alexllanas.openaudio.presentation.auth.ui.AuthFragmentDirections
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerState
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = NotificationManagerCompat.from(this)

//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add(R.id.nav_host_fragment, AuthFragment())
//        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val intentFilter = IntentFilter("com.alexllanas.openaudio.TOGGLE_PLAYBACK")
        this.registerReceiver(
            NotificationBroadcastReceiver(mediaPlayerViewModel, mainViewModel),
            intentFilter
        )
        lifecycleScope.launch(Dispatchers.IO) {
            mediaPlayerViewModel.mediaPlayerState.collect { state ->
                state.currentPlayingTrack?.let { _ ->
                    showNotification(state, notificationManager)
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

        if (mediaPlayerState.isPlaying) {
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
}
