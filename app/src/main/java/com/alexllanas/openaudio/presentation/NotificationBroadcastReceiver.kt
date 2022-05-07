package com.alexllanas.openaudio.presentation

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import kotlinx.coroutines.*

class NotificationBroadcastReceiver(
    private val mediaPlayerViewModel: MediaPlayerViewModel,
) :
    BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (mediaPlayerViewModel.isPlaying() || mediaPlayerViewModel.isBuffering()) {
            mediaPlayerViewModel.mediaPlayerState.value.youTubePlayer?.pause()
        } else {
            mediaPlayerViewModel.mediaPlayerState.value.youTubePlayer?.play()
        }
    }
}
