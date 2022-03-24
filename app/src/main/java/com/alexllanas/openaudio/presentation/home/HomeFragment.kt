package com.alexllanas.openaudio.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.actions.HomeAction
import com.alexllanas.openaudio.presentation.actions.HomeAction.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val actions = Channel<HomeAction>()
    private fun viewActions(): Flow<HomeAction> = actions.consumeAsFlow()
    private val actionFlow = merge(
        flowOf(login("testOpenAudio@gmail.com", "ducksquad1!")),
        flowOf(loadStream("whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI")),
        flowOf(getUserTracks("4d94501d1f78ac091dbc9b4d")),
        flowOf(search("bowie")),
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeActions()

        // Just testing
        login("testOpenAudio@gmail.com", "ducksquad1!")
        loadStream("whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI")
        getUserTracks("4d94501d1f78ac091dbc9b4d")
        search("bowie")

        return ComposeView(requireContext()).apply {
            setContent {
                SomeText()
            }
        }
    }

    @Suppress("FunctionName")
    @Composable
    private fun SomeText() {
        Text(
            text = "hello blank",
            color = Color.Cyan,
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace
        )
    }

    private fun render(state: HomeViewState) {
        Log.d(TAG, "render: ${state.loggedInUser?.name}")
        state.stream.forEach {
            Log.d(TAG, "render: $it")
        }
//        state.userTracks.forEach {
//            Log.d(TAG, "render: $it")
//        }
//        state.searchTrackResults.forEach {
//            Log.d(TAG, "render: $it")
//        }
    }

    private fun login(email: String, password: String): LoginAction {
        return LoginAction(email, password)
    }

    private fun loadStream(sessionToken: String): LoadStreamAction {
        return LoadStreamAction(sessionToken)
    }

    private fun getUserTracks(userId: String): GetUserTracksAction {
        return GetUserTracksAction(userId)
    }

    private fun search(query: String): SearchAction {
        return SearchAction(query)
    }

    private fun observeActions() {
        actionFlow.onEach {
            viewModel.dispatch(it)
        }
            .launchIn(lifecycleScope)
//        viewActions()
//            .onEach {
//                viewModel.dispatch(it)
//            }
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                render(state)
            }
        }
    }
}