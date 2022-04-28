package com.alexllanas.openaudio.presentation.home.ui

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.compose.components.LoadingIndicator
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.dataStore
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@Composable
fun StreamScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {

    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    val context = LocalContext.current


    mainState.loggedInUser?.id?.let { userId ->
        mainState.sessionToken?.let { token ->
            homeViewModel.dispatch(
                HomeAction.LoadStream(
                    userId,
//                "whydSid=s%3AdyOSwMKzpQJzr20IiEnErLq-FsQyqJDq.CTk77QTUqe1b4NyupQQsbI0CBrn57lpxoYxvBlJDVs8",
                    token
                )
            )
        }
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

            Log.d(TAG, "StreamScreen: ${homeState.isLoading}")

            if (homeState.stream.isEmpty()) {
                LoadingIndicator()
            } else {
                TrackList(
                    homeState.stream.toUI(),
                    onHeartClick = { track ->
                        track.id?.let { id ->
                            mainState.sessionToken?.let { token ->
                                mainState.loggedInUser?.id?.let { userId ->
                                    homeViewModel.refreshStream(id, token, userId)
                                }
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
    }
}



