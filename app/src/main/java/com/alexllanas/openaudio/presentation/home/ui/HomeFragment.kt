package com.alexllanas.openaudio.presentation.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeState
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    // for testing
    private val authViewModel: AuthViewModel by activityViewModels()

    private val actions = Channel<HomeAction>()
    private fun viewActions(): Flow<HomeAction> = actions.consumeAsFlow()
    private fun actionFlow(): Flow<HomeAction> = merge(
//        flowOf(login("testOpenAudio@gmail.com", "ducksquad1!")),
        flowOf(loadStream("whydSid=s%3Alcz9zAORxGMGh--F54iY6W-B-6Dh2GaX.dcbKBd8CjbvZNKiUzqrI3WaQrXW4qy3Xtm%2FQVZQWFjI")),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeActions()
//        viewModel.dispatch(HomeAction.SearchAction("cocteau"))

        mainViewModel.dispatch(
            AuthAction.Login.LoginAction(
                "testOpenAudio@gmail.com",
                "ducksquad1!"
            )
        )
        homeViewModel.dispatch(HomeAction.LoadStream("whydSid=s%3AbS7XKNXe0m5ZaNR7HIwCmtDSL_HDYIBx.KIlQu%2F0PrwEfbReaCMebbWKeE2LKRKznRTAzSxGidto"))
        return ComposeView(requireContext()).apply {
            setContent {
                StreamScreen(
                    homeViewModel = homeViewModel,
                    mainViewModel = mainViewModel,
                )
            }
        }
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