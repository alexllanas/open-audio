package com.alexllanas.openaudio.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationManagerCompat
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import kotlinx.coroutines.*

class NotificationBroadcastReceiver(
    private val mediaPlayerViewModel: MediaPlayerViewModel,
    private val contentView: RemoteViews

) :
    BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(TAG, "onReceive: received broadcast")
        if (mediaPlayerViewModel.mediaPlayerState.value.isPlaying) {
            mediaPlayerViewModel.mediaPlayerState.value.youTubePlayer?.pause()
            mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(false))
            contentView.setImageViewResource(
                R.id.track_image, com.google.android.exoplayer2.R.drawable.exo_controls_play
            )
        } else {
            mediaPlayerViewModel.mediaPlayerState.value.youTubePlayer?.play()
            mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(true))
            contentView.setImageViewResource(
                R.id.track_image, com.google.android.exoplayer2.R.drawable.exo_controls_pause
            )
        }
    }
}
