package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.state.SearchState
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.ui.navigateToPlaylistDetail
import com.alexllanas.openaudio.presentation.main.ui.navigateToUserDetail
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI

@Composable
fun SearchResultTabLayout(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainState: MainState,
    likeCallback : () -> Unit
) {
    val homeState by homeViewModel.homeState.collectAsState()
    var tabIndex = homeState.searchScreenState?.currentTab ?: 0
    val titles = listOf(
        "TRACKS",
        "PLAYLISTS",
        "USERS",
    )
    TabRow(selectedTabIndex = tabIndex, backgroundColor = MaterialTheme.colors.surface) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    homeViewModel.dispatch(HomeAction.SelectTab(index))
                    tabIndex = homeState.searchScreenState?.currentTab ?: 0
                },
                text = {
                    Text(text = title)
                }
            )
        }
    }
    when (tabIndex) {
        0 -> {
            if (homeState.searchTrackResults.isEmpty()) {
//                SearchBackground()
            } else {
                TrackList(homeState.searchTrackResults.toUI(),
                    onMoreClick = {
                        homeViewModel.dispatch(HomeAction.SelectTrack(it.toDomain()))
                        navHostController.navigate("track_options")
                    },
                    mainState = mainState,
                    onHeartClick = { track ->
                        track.id?.let { id ->
                            mainState.sessionToken?.let { token ->
                                homeViewModel.dispatch(
                                    HomeAction.ToggleLikeSearchTracks(
                                        trackId = id,
                                        token
                                    )
                                )
                                likeCallback()
                            }
                        }
                    })
            }
        }
        1 -> {
            if (homeState.searchPlaylistResults.isEmpty()) {
                SearchBackground()
            } else {
                PlaylistList(homeState.searchPlaylistResults) { selectedPlaylist ->
                    homeViewModel.dispatch(HomeAction.SelectPlaylist(selectedPlaylist))
                    navigateToPlaylistDetail(navHostController)
                }
            }
        }
        2 -> {
            if (homeState.searchUserResults.isEmpty()) {
                SearchBackground()
            } else {
                UserList(homeState.searchUserResults, { selectedUser ->
                    selectedUser.id?.let { userId ->
                        mainState.sessionToken?.let { sessionToken ->
                            homeViewModel.dispatch(HomeAction.GetUser(userId, sessionToken))
                        }
                    }
                    navigateToUserDetail(navHostController)
                }, onFollowClick = { isSubscribing, user ->
                    homeViewModel.onFollowClick(isSubscribing, user, mainState)
                })
            }
        }
    }
}


@Composable
fun SearchBackground() {
    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "search",
            modifier = Modifier.size(256.dp).padding(top = 96.dp).alpha(0.2f)
//                .clickable { state.focused = true }
        )
    }
}