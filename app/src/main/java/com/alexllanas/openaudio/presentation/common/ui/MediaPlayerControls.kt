package com.alexllanas.openaudio.presentation.common.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.NavController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.DarkPurple
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.ui.TrackDetailFragment
import com.alexllanas.openaudio.presentation.home.ui.TrackDetailScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.main.ui.MainFragment
import com.alexllanas.openaudio.presentation.main.ui.MainFragmentDirections
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.skydoves.landscapist.glide.GlideImage

private val youTubePlayerTracker = YouTubePlayerTracker()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaPlayerControls(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    fragmentManager: FragmentManager?,
    fragmentNavController: NavController
) {
    val mainState by mainViewModel.mainState.collectAsState()
//    val textScroll = rememberScrollState(0)
    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()
    var isPlaying by rememberSaveable { mutableStateOf(false) }
    var videoId by rememberSaveable { mutableStateOf("") }
    mediaPlayerState.isPlaying.let {
        isPlaying = it
    }

//    val youTubePlayerView = YouTubePlayerView(LocalContext.current)
//    youTubePlayerView.enableBackgroundPlayback(true)
//
//    mediaPlayerState.videoId.let {
//        if (videoId != it) {
//            youTubePlayerView.getYouTubePlayerWhenReady(object :
//                YouTubePlayerCallback {
//                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
//                    if (it.startsWith("/yt/")) {
//                        mediaPlayerViewModel.dispatch(HomeAction.SetYoutubePlayer(youTubePlayer))
//                        youTubePlayer.loadVideo(it.removePrefix("/yt/"), 0f)
//                        isPlaying = true
//                        mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(isPlaying))
//                    }
//                }
//            })
//            videoId = it
//        }
//    }

    mediaPlayerState.currentPlayingTrack?.let {
        Card(
            modifier = modifier.padding(8.dp).height(64.dp)
                .clickable {
                    mediaPlayerState.currentPlayingTrack?.let {
                        fragmentNavController.navigate(MainFragmentDirections.actionMainFragmentToTrackDetailFragment())
                    }
                },
            shape = RoundedCornerShape(8.dp),
            backgroundColor = DarkPurple,
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                GlideImage(
                    modifier = Modifier.size(50.dp),
                    imageModel = mediaPlayerState.currentPlayingTrack?.image,
                    contentScale = ContentScale.Crop,
                    placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                    error = ImageBitmap.imageResource(R.drawable.blank_user)
                )
//                AndroidView(
//                    factory = {
//                        youTubePlayerView
//                    },
//                    modifier = Modifier.size(0.dp)
//                )
                Text(
                    text = mediaPlayerState.currentPlayingTrack?.title ?: "Track Title",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
//                        .horizontalScroll(textScroll)
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp)
                        .clickable {
                            if (isPlaying) {
                                mediaPlayerState.youTubePlayer?.pause()
                                isPlaying = false
                                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(false))
                            } else {
                                mediaPlayerState.youTubePlayer?.play()
                                isPlaying = true
                                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(true))
                            }

                        },
                    imageVector = if (isPlaying)
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.play_pause_button)
                )
            }
        }
    }

}

