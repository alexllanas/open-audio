package com.alexllanas.openaudio.presentation.home.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.MediaControlPanel
import com.alexllanas.openaudio.presentation.compose.components.SeekBar
import com.alexllanas.openaudio.presentation.compose.theme.GreenTint
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun TrackDetailScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    fragmentNavController: NavController,
    navHostController: NavHostController
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val homeState by homeViewModel.homeState.collectAsState()
    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()
    Scaffold(topBar = {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = stringResource(R.string.close),
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        fragmentNavController.popBackStack()
                    }
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.close),
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navHostController.navigate(TrackNavItem.TrackOptions.screenRoute)
                    }
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            mediaPlayerState.currentPlayingTrack?.let { track ->
            homeState.selectedTrack?.let { track ->
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .weight(1f, true),
                    model =
                    URLDecoder.decode(
                        track.image?: "",
                        StandardCharsets.UTF_8.toString()
                    ),
                    contentDescription = stringResource(R.string.track_image),
                    placeholder = painterResource(R.drawable.blank_user),
                    error = painterResource(R.drawable.blank_user),
                    contentScale = ContentScale.FillHeight
                )
                Column(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .weight(2f, true),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${track.title}",
                            style = MaterialTheme.typography.h5,
                            fontWeight = Bold
                        )
                        mainState.loggedInUser?.let { user ->
                            if (track.liked) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    tint = GreenTint,
                                    contentDescription = stringResource(R.string.favorite),
                                    modifier = Modifier.clickable {
                                        track.id?.let { trackId ->
                                            Log.d(TAG, "TrackDetailScreen: ${track.liked}")
//                                            mainState.sessionToken?.let { token ->
//                                                mediaPlayerViewModel.dispatch(
//                                                    HomeAction.ToggleCurrentTrackLike(
//                                                        trackId = trackId,
//                                                        sessionToken = token
//                                                    )
//                                                )
//                                            }
                                            mainState.sessionToken?.let { token ->
                                                user.id?.let { userId ->
                                                    homeViewModel.toggleTrackOptionsLike(
                                                        trackId,
                                                        token,
                                                        userId
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    tint = MaterialTheme.colors.onBackground,
                                    contentDescription = stringResource(R.string.unfavorite),
                                    modifier = Modifier.clickable {
                                        Log.d(TAG, "TrackDetailScreen: ${track.liked}")
                                        track.id?.let { trackId ->
                                            mainState.sessionToken?.let { token ->
                                                user.id?.let { userId ->
                                                    homeViewModel.toggleTrackOptionsLike(
                                                        trackId,
                                                        token,
                                                        userId
                                                    )
//                                                    mediaPlayerViewModel.dispatch(
//                                                        HomeAction.ToggleCurrentTrackLike(
//                                                            trackId = trackId,
//                                                            sessionToken = token
//                                                        )
//                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                    SeekBar(modifier = Modifier.padding(top = 64.dp), mediaPlayerViewModel)
                    MediaControlPanel(
                        modifier = Modifier
                            .fillMaxWidth(),
                        mediaPlayerViewModel = mediaPlayerViewModel
                    )
                }
            }
        }
    }
}