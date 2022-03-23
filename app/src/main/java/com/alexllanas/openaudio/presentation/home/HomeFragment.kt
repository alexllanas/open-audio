package com.alexllanas.openaudio.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeActions()
        loadStream("whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI")
        getUserTracks("4d94501d1f78ac091dbc9b4d")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun render(state: HomeViewState) {
        state.stream.forEach {
//            Log.d(TAG, "render: $it")
        }
        state.userTracks.forEach {
            Log.d(TAG, "render: $it")
        }
    }

    private fun loadStream(sessionToken: String) {
        lifecycleScope.launch {
            actions.send(LoadStream(sessionToken))
        }
    }

    private fun getUserTracks(userId: String) {
        lifecycleScope.launch {
            actions.send(GetUserTracks(userId))
        }
    }

    private fun observeActions() {
        viewActions()
            .onEach {
                viewModel.dispatch(it)
            }
            .launchIn(lifecycleScope)
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                render(state)
            }
        }
    }
}