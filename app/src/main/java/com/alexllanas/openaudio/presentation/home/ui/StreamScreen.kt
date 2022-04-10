package com.alexllanas.openaudio.presentation.home.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel

@Composable
fun StreamScreen(homeViewModel: HomeViewModel, mainViewModel: MainViewModel) {

    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Your Stream")
                })
        }
    ) {
        TrackList(
            homeState.stream,
            onHeartClick = { shouldLike, track ->
                homeViewModel.onHeartClick(
                    shouldLike,
                    track,
                    mainState.loggedInUser,
                    mainState.sessionToken
                )
            }
        )
    }
}



