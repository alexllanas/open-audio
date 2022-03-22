package com.alexllanas.openaudio.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.actions.HomeAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private val actions = Channel<HomeAction>()
    private fun viewActions(): Flow<HomeAction> = actions.consumeAsFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindActions()
        loadStream("whydSid=s%3AC05hk5_esLk9IkRq8x7ugyZopizvrtwp.Ld3rygEAWQEpWYI08b9eZbwe0t8bsYO44TNvAEsj9DU")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun render(state: HomeViewState) {
        state.stream.forEach{
            Log.d(TAG, "render: $it")
        }
    }

    private fun loadStream(sessionToken: String) {
        lifecycleScope.launch {
            actions.send(HomeAction.LoadStream(sessionToken))
        }
    }

    private fun bindActions() {
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