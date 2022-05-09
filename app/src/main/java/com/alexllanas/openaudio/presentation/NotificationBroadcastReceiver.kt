package com.alexllanas.openaudio.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
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
