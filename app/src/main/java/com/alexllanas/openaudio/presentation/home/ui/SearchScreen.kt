package com.alexllanas.openaudio.presentation.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.presentation.compose.components.SearchResultTabLayout
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeState
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import kotlinx.coroutines.delay


@Composable
fun SearchScreen(homeViewModel: HomeViewModel, mainViewModel: MainViewModel) {
    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

//    var searchedText by remember { mutableStateOf(homeState.query) }
//
//    Column {
//        TextField(
//            value = searchedText,
//            onValueChange = { query ->
//                searchedText = query
//                homeViewModel.dispatch(HomeAction.SearchAction(query))
//            },
//            maxLines = 1,
//            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
//            label = { Text(stringResource(R.string.search_tracks_users_playlists)) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    horizontal = 8.dp
//                )
//        )
//        TrackList(
//            homeState.searchTrackResults,
//            onHeartClick = { shouldLike, track ->
//                homeViewModel.onHeartClick(
//                    shouldLike,
//                    track,
//                    mainState.loggedInUser,
//                    mainState.sessionToken
//                )
//            }
//        )
//    }

    Scaffold(
        topBar = {
            SearchBar(modifier = Modifier, homeViewModel, homeState = homeState)
        }
    ) {
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    state: SearchState = rememberSearchState(),
    homeState: HomeState
) {

    Column(
        modifier = modifier.fillMaxSize()
            .padding(top = 5.dp)
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { state.query = it },
            onSearchFocusChange = { state.focused = it },
            onClearQuery = { state.query = TextFieldValue("") },
            onBack = { state.query = TextFieldValue("") },
//            onBack = { },
            searching = state.searching,
            focused = state.focused,
            modifier = modifier
        )
        LaunchedEffect(state.query.text) {
            state.searching = true
            delay(100)
            viewModel.dispatch(HomeAction.SearchAction(state.query.text))
            state.trackResults = homeState.searchTrackResults
            state.playlistResults = homeState.searchPlaylistResults
            state.userResults = homeState.searchUserResults
            state.searching = false
        }
        when (state.searchDisplay) {
            SearchDisplay.Initial -> {
                SearchResultTabLayout(state)
            }
            SearchDisplay.Results -> {
                SearchResultTabLayout(state)
            }
            SearchDisplay.NoResults -> {
            }
        }
    }
}

