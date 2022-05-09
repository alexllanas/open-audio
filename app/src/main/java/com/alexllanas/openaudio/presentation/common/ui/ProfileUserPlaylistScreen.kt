package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.LoadingIndicator
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun ProfileUserPlaylistScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    playerViewModel: MediaPlayerViewModel,
    mainState: MainState,
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    mainState.profileUserPlaylist?.url?.let { url ->
        mainViewModel.dispatch(HomeAction.GetProfileUserPlaylistTracks(url))
    }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Color.Transparent) {
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp).clickable {
                        navController.popBackStack()
                    },
                    imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                        R.string.back_arrow
                    )
                )
            }
        }
    },
        bottomBar = { BottomNav(navController = navController) }
    ) {
        mainState.profileUserPlaylist?.let {
            Column(modifier = Modifier) {
                ProfilePlaylistHeader(modifier, it.toUI())
                TrackList(
                    tracks = mainState.profileUserPlaylistTracks.toUI(),
                    onHeartClick = { track ->
                        track.id?.let { id ->
                            mainState.sessionToken?.let { token ->
                                mainState.profileUserPlaylist.url?.let { url ->
                                    mainViewModel.dispatch(
                                        HomeAction.ToggleCurrentUserPlaylistTrackLike(
                                            id,
                                            token
                                        )
                                    )
                                    mainViewModel.dispatch(
                                        HomeAction.GetCurrentUserPlaylistTracks(
                                            url
                                        )
                                    )

                                }
                            }
                        }
                    },
                    onMoreClick = {
                        homeViewModel.dispatch(HomeAction.SelectTrack(it.toDomain()))
                        navController.navigate("track_options")
                    },
                    mainState = mainState,
                    mainViewModel = mainViewModel,
                    playerViewModel = playerViewModel,
                    homeViewModel = homeViewModel
                )
            }
        } ?: LoadingIndicator()
    }
}

@Composable
fun ProfileUserPlaylistHeader(modifier: Modifier = Modifier, playlist: PlaylistUIModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            modifier = Modifier.size(160.dp)
                .padding(top = 16.dp, bottom = 4.dp),
            imageModel = URLDecoder.decode(playlist.coverImage, StandardCharsets.UTF_8.toString()),
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
            error = ImageBitmap.imageResource(R.drawable.blank_user)
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = playlist.name.toString(),
            style = MaterialTheme.typography.h5
        )
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        ) {
            GlideImage(
                modifier = Modifier.size(24.dp).clip(CircleShape),
                imageModel = URLDecoder.decode(
                    playlist.author?.avatarUrl ?: "",
                    StandardCharsets.UTF_8.toString()
                ),
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = playlist.author?.name.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
