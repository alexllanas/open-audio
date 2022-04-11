package com.alexllanas.openaudio.presentation.compose.components

import android.os.Bundle
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.ui.SearchState
import com.alexllanas.openaudio.presentation.main.ui.NavItem
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.google.gson.Gson

@Composable
fun SearchResultTabLayout(state: SearchState, navHostController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }
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
                    tabIndex = index
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

            val json = Gson().toJson(selectedPlaylist)
            Log.d(TAG, "SearchResultTabLayout: $json")
            navHostController.navigate("playlist_detail/$json")
        }
        2 -> UserList(state.userResults, {})
    }
}
