package com.alexllanas.openaudio.presentation.common.ui

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.OpenAudioAppBar
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerState
import com.alexllanas.openaudio.presentation.main.ui.NavItem

@Composable
fun FollowScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mediaPlayerState: MediaPlayerState,
    mainState: MainState,
    title: String
) {
    Log.d(TAG, "FollowScreen: ")
    
    val homeState by homeViewModel.homeState.collectAsState()
    val context = LocalContext.current
    val followState = when (title) {
        Constants.FOLLOWERS -> FollowState.FOLLOWERS
        else -> FollowState.FOLLOWING
    }

    Scaffold(
        topBar = {
            OpenAudioAppBar(
                barTitle = title,
                navigationAction = { navHostController.popBackStack() },
                navigationIcon = Icons.Default.ArrowBack,
                false
            )

        },
        bottomBar = { BottomNav(navController = navHostController) }

    ) {
        UserList(
            when (followState) {
                FollowState.FOLLOWERS -> homeState.selectedUserFollowers
                FollowState.FOLLOWING -> homeState.selectedUserFollowing
            }, onUserClick = { user ->
                user.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        homeViewModel.dispatch(HomeAction.GetUser(id, token))
                        navHostController.navigate(NavItem.UserDetail.screenRoute)
                    }
                }
            },
            onFollowClick = { isSubscribing, user ->
                homeViewModel.onFollowClick(isSubscribing, user, mainState, context)

                homeState.selectedUser?.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        when (followState) {
                            FollowState.FOLLOWERS -> {
                                homeViewModel.dispatch(
                                    HomeAction.GetFollowers(
                                        id,
                                        token
                                    )
                                )
                            }
                            FollowState.FOLLOWING -> {
                                homeViewModel.dispatch(
                                    HomeAction.GetFollowing(
                                        id,
                                        token
                                    )
                                )
                            }
                        }

                    }
                }
            },
            mediaPlayerState = mediaPlayerState
        )
    }
}

enum class FollowState {
    FOLLOWERS, FOLLOWING
}