package com.alexllanas.openaudio.presentation.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alexllanas.core.util.Constants.Companion.TAG
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
//        flowOf(getUserTracks("4d94501d1f78ac091dbc9b4d")),
//        flowOf(search("bowie")),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeActions()
//        login("testOpenAudio@gmail.com", "ducksquad1!")
        viewModel.dispatch(HomeAction.SearchAction("grouper"))
//        viewModel.dispatch(HomeAction.GetUserTracksAction("4d94501d1f78ac091dbc9b4d"))
//        viewModel.dispatch(HomeAction.LoadStreamAction("whydSid=s%3AbS7XKNXe0m5ZaNR7HIwCmtDSL_HDYIBx.KIlQu%2F0PrwEfbReaCMebbWKeE2LKRKznRTAzSxGidto"))

        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen()
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModel.dispatch(HomeAction.LoginAction(email, password))
    }

    @Suppress("FunctionName")
    @Composable
    fun MainScreen() {
        val state by viewModel.state.collectAsState()
        state.loggedInUser?.name?.let {
            SomeText(it)
        }
        Column {
            state.searchTrackResults.forEach {
                it?.title?.let { it1 -> Text(it1) }
            }
        }
    }

    @Suppress("FunctionName")
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


    private fun loadStream(sessionToken: String): HomeAction.LoadStreamAction {
        return HomeAction.LoadStreamAction(sessionToken)
    }

    private fun getUserTracks(userId: String): HomeAction.GetUserTracksAction {
        return HomeAction.GetUserTracksAction(userId)
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

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                render(state)
            }
        }
    }
}