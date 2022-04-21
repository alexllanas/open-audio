package com.alexllanas.openaudio.presentation.home.ui

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI

@Composable
fun StreamScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {

    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Your Stream")
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        bottomBar = { BottomNav(navController = navController) }

    ) {

        Log.d(TAG, "StreamScreen: ${homeState.stream}")

        TrackList(
            homeState.stream.toUI(),
            onHeartClick = { track ->
                track.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        homeViewModel.dispatch(
                            HomeAction.ToggleLikeStreamTrack(
                                trackId = id,
                                token
                            )
                        )
                    }
                }
            },
            onMoreClick = {
                homeViewModel.dispatch(HomeAction.SelectTrack(it.toDomain()))
                navController.navigate("track_options")
            },
            mainState = mainState
        )
    }
}



