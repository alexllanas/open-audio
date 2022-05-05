package com.alexllanas.openaudio.presentation.home.ui

import android.graphics.Paint
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.heartIconTint
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.main.ui.NavItem
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            mediaPlayerState.currentPlayingTrack?.let { track ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model =
                    URLDecoder.decode(
                        track.image,
                        StandardCharsets.UTF_8.toString()
                    ),
                    contentDescription = stringResource(R.string.track_image),
                    placeholder = painterResource(R.drawable.blank_user),
                    error = painterResource(R.drawable.blank_user),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    modifier = Modifier.padding(top = 48.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier,
                        text = "${track.title}",
                        style = MaterialTheme.typography.subtitle1
                    )
                    mainState.loggedInUser?.let { user ->
                        if (track.userLikeIds.contains(user.id)) {
                            track.liked = true
                        }
                        if (track.liked) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                tint = heartIconTint,
                                contentDescription = stringResource(R.string.favorite),
                                modifier = Modifier.clickable {
                                    track.id?.let { trackId ->
                                        mainState.sessionToken?.let { token ->
                                            mediaPlayerViewModel.dispatch(
                                                HomeAction.ToggleCurrentTrackLike(
                                                    trackId = trackId,
                                                    sessionToken = token
                                                )
                                            )
                                            homeViewModel.dispatch(
                                                HomeAction.ToggleTrackOptionsLike(
                                                    trackId = trackId,
                                                    sessionToken = token
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                tint = heartIconTint,
                                contentDescription = stringResource(R.string.unfavorite),
                                modifier = Modifier.clickable {
                                    track.id?.let { trackId ->
                                        mainState.sessionToken?.let { token ->
                                            mediaPlayerViewModel.dispatch(
                                                HomeAction.ToggleCurrentTrackLike(
                                                    trackId = trackId,
                                                    sessionToken = token
                                                )
                                            )
                                            homeViewModel.dispatch(
                                                HomeAction.ToggleTrackOptionsLike(
                                                    trackId = trackId,
                                                    sessionToken = token
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}