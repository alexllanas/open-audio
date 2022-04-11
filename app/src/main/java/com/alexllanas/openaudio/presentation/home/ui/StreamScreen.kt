package com.alexllanas.openaudio.presentation.home.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI

@Composable
fun StreamScreen(homeViewModel: HomeViewModel, mainViewModel: MainViewModel) {

    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Your Stream")
                },
            backgroundColor = MaterialTheme.colors.surface)
        }
    ) {
        TrackList(
            homeState.stream.toUI(),
            onHeartClick = { shouldLike, track ->
                homeViewModel.onHeartClick(
                    shouldLike,
                    track.toDomain(),
                    mainState.loggedInUser,
                    mainState.sessionToken
                )
            },
            onMoreClick = {}
        )
    }
}



