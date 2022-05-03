package com.alexllanas.openaudio.presentation.main.ui

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants.Companion.CHANNEL_ID
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.NotificationBroadcastReceiver
import com.alexllanas.openaudio.presentation.auth.ui.AuthFragment
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = NotificationManagerCompat.from(this)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.nav_host_fragment, AuthFragment())
        }

        val intentFilter = IntentFilter("com.alexllanas.openaudio.TOGGLE_PLAYBACK")
        this.registerReceiver(
            NotificationBroadcastReceiver(mediaPlayerViewModel),
            intentFilter
        )
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            mainViewModel.mainState.collect {
                Log.d(TAG, "onCreate: ${it.currentPlayingTrack}")
                showNotification(it.currentPlayingTrack, notificationManager)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private suspend fun showNotification(
        track: Track?,
        notificationManager: NotificationManagerCompat
    ) {
//        contentView.setImageViewResource(R.id.track_image, R.drawable.blank_user)
        var bitmap: Bitmap? = null
        lifecycleScope.launch(Dispatchers.IO) {
            track?.image?.let {
                val url = URL(track.image)
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }
        }
        val intent = Intent()
        intent.action = "com.alexllanas.openaudio.TOGGLE_PLAYBACK"
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.blank_user)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .addAction(
                com.google.android.exoplayer2.ui.R.drawable.exo_controls_pause,
                "Pause",
                pendingIntent
            )
//            .setContentTitle(track?.title ?: "Wonderful music")
//            .setContentText("My Awesome Band")
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
            )
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(bitmap)
            .build()

        mediaPlayerViewModel.mediaPlayerState.collect {
            if (it.isPlaying) {
                notification.actions[0] = Notification.Action(
                    com.google.android.exoplayer2.ui.R.drawable.exo_controls_pause,
                    "pause",
                    pendingIntent
                )
            } else {
                notification.actions[0] = Notification.Action(
                    com.google.android.exoplayer2.ui.R.drawable.exo_controls_play,
                    "play",
                    pendingIntent
                )
            }
            notificationManager.notify(1, notification)
        }
    }
}
