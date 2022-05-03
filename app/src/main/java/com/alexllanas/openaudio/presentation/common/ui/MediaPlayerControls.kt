package com.alexllanas.openaudio.presentation.common.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MediaPlayerControls(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()
    var isPlaying by rememberSaveable { mutableStateOf(false) }
//    var videoId by rememberSaveable { mutableStateOf("") }

    val youTubePlayerView = YouTubePlayerView(LocalContext.current)
    youTubePlayerView.enableBackgroundPlayback(true)

//    mainState.currentPlayingTrack?.mediaUrl?.let {
    mediaPlayerState.videoId.let {
//        if (videoId != it) {
        youTubePlayerView.getYouTubePlayerWhenReady(object :
            YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                Log.d(TAG, "onYouTubePlayer: $youTubePlayer")
                if (it.startsWith("/yt/")) {
                    mainViewModel.dispatch(HomeAction.SetYoutubePlayer(youTubePlayer))
//                    videoId = it
                    youTubePlayer.loadVideo(it.removePrefix("/yt/"), 0f)
                    isPlaying = true
                }
            }
        })
//        }
    }

    Card(
        modifier = modifier.padding(8.dp).height(64.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            GlideImage(
                modifier = Modifier.size(50.dp),
                imageModel = mainState.currentPlayingTrack?.image,
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
            AndroidView(
                factory = {
                    youTubePlayerView
                },
                modifier = Modifier.size(0.dp)
            )
            Text(
                text = mainState.currentPlayingTrack?.title ?: "Track Title",
                modifier = Modifier.weight(1f).padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .clickable {
                        if (isPlaying) {
                            mainState.youTubePlayer?.pause()
                        } else {
                            mainState.youTubePlayer?.play()
                        }
                        isPlaying = !isPlaying
                    },
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = stringResource(R.string.play_pause_button)
            )
        }
    }

}

