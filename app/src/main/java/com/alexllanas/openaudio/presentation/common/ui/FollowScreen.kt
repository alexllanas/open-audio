package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.compose.components.OpenAudioAppBar
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.main.ui.navigateToUserDetail

@Composable
fun FollowScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainState: MainState,
    title: String
) {
    val homeState by homeViewModel.homeState.collectAsState()

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
            when (title) {
                Constants.FOLLOWERS -> homeState.selectedUserFollowers
                Constants.FOLLOWING -> homeState.selectedUserFollowing
                else -> emptyList()
            }, onUserClick = { user ->
                user.id?.let { id ->
                    mainState.sessionToken?.let { token ->
                        homeViewModel.dispatch(HomeAction.GetUser(id, token))
                        navigateToUserDetail(navHostController)
                    }
                }
            },
            onFollowClick = { isSubscribing, user ->
                homeViewModel.onFollowClick(isSubscribing, user, mainState)
            }
        )
    }
}