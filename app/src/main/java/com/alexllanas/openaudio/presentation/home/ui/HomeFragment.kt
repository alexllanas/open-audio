package com.alexllanas.openaudio.presentation.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.compose.OpenAudioTheme
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeState
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val actions = Channel<HomeAction>()
    private fun viewActions(): Flow<HomeAction> = actions.consumeAsFlow()
    private fun actionFlow(): Flow<HomeAction> = merge(
//        flowOf(login("testOpenAudio@gmail.com", "ducksquad1!")),
        flowOf(loadStream("whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI")),
//        flowOf(getUserTracks("4d94501d1f758ac091dbc9b4d")),
//        flowOf(search("bowie")),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeActions()
        viewModel.dispatch(HomeAction.SearchAction("cocteau"))
        viewModel.dispatch(HomeAction.LoadStream("whydSid=s%3AbS7XKNXe0m5ZaNR7HIwCmtDSL_HDYIBx.KIlQu%2F0PrwEfbReaCMebbWKeE2LKRKznRTAzSxGidto"))

        return ComposeView(requireContext()).apply {
            setContent {
                OpenAudioTheme {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val state by viewModel.homeState.collectAsState()
        val scrollState = rememberScrollState()

`

        Column(
            modifier = Modifier.scrollable(
                scrollState,
                orientation = Orientation.Vertical,
            ),
        ) {
            Column {
                UserList(state.searchUserResults)
//                PlaylistList(state.searchPlaylistResults)
//                TrackList(state.stream)
//                TrackList(state.searchTrackResults)
            }
        }
    }

    @Composable
    fun TrackList(items: List<Track>) {
        LazyColumn {
            items(items) { track ->
                track.title?.let { Text(it) }
            }
        }
    }

    @Composable
    fun PlaylistList(items: List<Playlist>) {
        LazyColumn {
            items(items) { playlist ->
                playlist.name?.let { Text(it) }
            }
        }
    }

    @Composable
    fun UserList(items: List<User>) {
        LazyColumn {
            items(items) { user ->
                user.name?.let { Text(it) }
            }
        }
    }

    @Composable
    private fun SomeText(name: String) {
        Column {
            Text(
                text = "hello $name",
                color = Color.Cyan,
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "hello blank",
                color = Color.Cyan,
                fontSize = 22.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }

    private fun render(state: HomeState) {
//        Log.d(TAG, "render: LOGIN ======== ${state.loggedInUser?.name}")
//        state.stream.forEach {
//            Log.d(TAG, "render: STREAM === $it")
//        }
//        state.userTracks.forEach {
//            Log.d(TAG, "render: USER TRACKS === $it")
//        }
        state.searchTrackResults.forEach {
            Log.d(TAG, "render: SEARCH === $it")
        }
//        state.searchUserResults.forEach {
//            Log.d(TAG, "render: SEARCH === ${it.name}")
//        }
//        state.searchPostResults.forEach {
//            Log.d(TAG, "render: SEARCH === $it")
//        }
//        state.searchPlaylistResults.forEach {
//            Log.d(TAG, "render: SEARCH === ${it.name}")
//        }
    }


    private fun loadStream(sessionToken: String): HomeAction.LoadStream {
        return HomeAction.LoadStream(sessionToken)
    }

    private fun getUserTracks(userId: String): HomeAction.GetUserTracks {
        return HomeAction.GetUserTracks(userId)
    }

    private fun search(query: String): HomeAction.SearchAction {
        return HomeAction.SearchAction(query)
    }

    private fun observeActions() {
//        actionFlow()
//        viewActions()
//            .onEach {
//                viewModel.dispatch(it)
//            }
//            .launchIn(lifecycleScope)

//    lifecycleScope.launchWhenStarted {
//        viewModel.homeState.collect { state ->
//            render(state)
//        }
//    }
    }
}