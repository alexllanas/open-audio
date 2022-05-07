package com.alexllanas.openaudio.presentation.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.compose.components.lists.UserList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.state.SearchState
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.main.ui.navigateToPlaylistDetail
import com.alexllanas.openaudio.presentation.main.ui.navigateToUserDetail
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchResultTabLayout(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    playerViewModel: MediaPlayerViewModel,
    mainState: MainState,
    likeCallback: () -> Unit
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val mediaPlayerState by playerViewModel.mediaPlayerState.collectAsState()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState()
    val tabIndex by remember { mutableStateOf(0) }
    val titles = listOf(
        "TRACKS",
        "PLAYLISTS",
        "USERS",
    )
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = MaterialTheme.colors.surface,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
//                    homeViewModel.dispatch(HomeAction.SelectTab(index))
                    coroutineScope.launch {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        pagerState.animateScrollToPage(index)
                    }

                },
                text = {
                    Text(text = title)
                }
            )
        }
    }
    HorizontalPager(
        count = titles.size,
        state = pagerState,
    ) { index ->
//        keyboardController?.hide()
//        focusManager.clearFocus()
//        homeViewModel.dispatch(HomeAction.SelectTab(index))
        when (index) {
            0 -> {
                if (homeState.searchTrackResults.isEmpty()) {
                    SearchBackground()
                } else {
                    TrackList(
                        homeState.searchTrackResults.toUI(),
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
                        },
                        mainViewModel = mainViewModel,
                        playerViewModel = playerViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
            1 -> {
                if (homeState.searchPlaylistResults.isEmpty()) {
                    SearchBackground()
                } else {
                    PlaylistList(
                        playlists = homeState.searchPlaylistResults,
                        isCountVisible = true,
                        mediaPlayerState = mediaPlayerState
                    ) { selectedPlaylist ->
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
                                homeViewModel.dispatch(
                                    HomeAction.GetUser(
                                        userId,
                                        sessionToken
                                    )
                                )
                            }
                        }
                        navigateToUserDetail(navHostController)
                    }, onFollowClick = { isSubscribing, user ->
                        homeViewModel.onFollowClick(isSubscribing, user, mainState, context)
                    },
                        mediaPlayerState = mediaPlayerState
                    )
                }
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