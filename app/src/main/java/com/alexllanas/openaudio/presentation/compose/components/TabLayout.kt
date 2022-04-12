package com.alexllanas.openaudio.presentation.compose.components

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.ui.SearchState
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.ui.navigateToPlaylist
import com.alexllanas.openaudio.presentation.main.ui.navigateToUserDetail
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchResultTabLayout(
    state: SearchState,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainState: MainState
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
        0 -> TrackList(state.trackResults.toUI())
        1 -> PlaylistList(state.playlistResults) { selectedPlaylist ->
            homeViewModel.dispatch(HomeAction.SelectPlaylist(selectedPlaylist))
            navigateToPlaylist(navHostController)
        }
        2 -> UserList(state.userResults, { selectedUser ->
            selectedUser.id?.let { userId ->
                mainState.sessionToken?.let { sessionToken ->
                    homeViewModel.dispatch(HomeAction.GetUser(userId, sessionToken))
                }
            }
//            homeViewModel.dispatch(HomeAction.SelectUser(selectedUser))
            navigateToUserDetail(navHostController)
        })
    }
}


