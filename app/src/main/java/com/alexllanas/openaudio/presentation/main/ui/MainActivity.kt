package com.alexllanas.openaudio.presentation.main.ui

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.alexllanas.core.util.Constants.Companion.CHANNEL_ID
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.NotificationBroadcastReceiver
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.auth.ui.AuthFragment
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = RemoteViews(packageName, R.layout.notification_media_controls)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.nav_host_fragment, AuthFragment())
        }

        val intentFilter = IntentFilter("com.alexllanas.openaudio.TOGGLE_PLAYBACK")
        contentView.setImageViewResource(
            R.id.play_pause_button,
            com.google.android.exoplayer2.R.drawable.exo_controls_play
        )
        this.registerReceiver(NotificationBroadcastReceiver(mediaPlayerViewModel, contentView), intentFilter)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            mainViewModel.mainState.collect {
                showNotification(it.currentPlayingTrack?.title, contentView)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showNotification(trackTitle: String?, contentView: RemoteViews) {

        contentView.setImageViewResource(R.id.track_image, R.drawable.blank_user)
        contentView.setTextViewText(
            R.id.track_title,
            trackTitle ?: "track title"
        )
        val intent = Intent()
        intent.action = "com.alexllanas.openaudio.TOGGLE_PLAYBACK"
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        contentView.setOnClickPendingIntent(R.id.play_pause_button, pendingIntent)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.blank_user)
            .setCustomContentView(contentView)
            .setContentTitle("OpenAudio Media Controls")
            .setContentText("Control OpenAudio playback here..")
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .build()
        NotificationManagerCompat.from(this).notify(1, notification)
    }

}
