package com.alexllanas.openaudio.presentation.common.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.presentation.compose.components.appbars.TitleBackBar
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerState

@Composable
fun AddToPlaylistScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    mediaPlayerState: MediaPlayerState,
    navHostController: NavHostController,
    createPlaylistRoute: String
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = { TitleBackBar("Add to playlist") { navHostController.popBackStack() } }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Button(onClick = {
                    navHostController.navigate(createPlaylistRoute)
                }) {
                    Text(text = "New Playlist")
                }
            }
            PlaylistList(
                playlists = mainState.loggedInUser?.playlists ?: emptyList(),
                false,
                mediaPlayerState = mediaPlayerState
            ) { playlist ->
                playlist.id?.let { id ->
                    playlist.name?.let { name ->
                        homeState.selectedTrack?.let { track ->
                            mainState.sessionToken?.let { token ->
                                mainViewModel.dispatch(
                                    HomeAction.AddTrackToPlaylist(
                                        track = track,
                                        playlistName = name,
                                        playListId = id,
                                        sessionToken = token
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    "Added to ${playlist.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navHostController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}