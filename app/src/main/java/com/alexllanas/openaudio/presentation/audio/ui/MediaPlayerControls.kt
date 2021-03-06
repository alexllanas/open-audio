package com.alexllanas.openaudio.presentation.common.ui

import android.icu.text.CaseMap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.DarkPurple
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.main.ui.MainFragmentDirections
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
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
    mediaPlayerState.isPlaying.let {
        isPlaying = it
    }

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
                Text(
                    text = if (mediaPlayerState.playbackState == PlayerConstants.PlayerState.BUFFERING) "Buffering or Advertisement"
                    else mediaPlayerState.currentPlayingTrack?.title ?: "Track CaseMap.Title",
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
                            if (mediaPlayerViewModel.isPlaying() || mediaPlayerViewModel.isBuffering()) {
                                mediaPlayerState.youTubePlayer?.pause()
                                isPlaying = false
                                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(false))
                            } else {
                                mediaPlayerState.youTubePlayer?.play()
                                isPlaying = true
                                mediaPlayerViewModel.dispatch(HomeAction.SetIsPlaying(true))
                            }

                        },
                    imageVector = if (mediaPlayerViewModel.isPlaying())
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.play_pause_button)
                )
            }
        }
    }

}

