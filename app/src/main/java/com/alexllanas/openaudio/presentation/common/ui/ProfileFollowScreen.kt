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
fun ProfileFollowScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mediaPlayerState: MediaPlayerState,
    mainState: MainState,
    title: String
) {
    Log.d(TAG, "ProfileFollowScreen: ")
    val homeState by homeViewModel.homeState.collectAsState()
    val context = LocalContext.current
    val followState = when (title) {
        Constants.FOLLOWERS -> ProfileFollowState.FOLLOWERS
        else -> ProfileFollowState.FOLLOWING
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
                ProfileFollowState.FOLLOWERS -> homeState.selectedProfileFollowers
                ProfileFollowState.FOLLOWING -> homeState.selectedProfileFollowing
            }, onUserClick = { user ->
                user.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        homeViewModel.dispatch(HomeAction.GetProfileScreenUser(id, token))
                        navHostController.navigate(NavItem.ProfileUser.screenRoute)
                    }
                }
            },
            onFollowClick = { isSubscribing, user ->
                homeViewModel.onFollowClick(isSubscribing, user, mainState, context)

                homeState.selectedProfileScreenUser?.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        // refresh user list with updated value
                        when (followState) {
                            ProfileFollowState.FOLLOWERS -> {
                                homeViewModel.dispatch(
                                    HomeAction.GetProfileFollowers(
                                        id,
                                        token
                                    )
                                )
                            }
                            ProfileFollowState.FOLLOWING -> {
                                homeViewModel.dispatch(
                                    HomeAction.GetProfileFollowing(
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

enum class ProfileFollowState {
    FOLLOWERS, FOLLOWING
}