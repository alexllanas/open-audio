package com.alexllanas.openaudio.presentation.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.asLiveData
import androidx.navigation.compose.rememberNavController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.dataStore
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.MainFragment
import kotlinx.coroutines.flow.map

class AuthFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        val tokenFlow = activity?.dataStore?.data?.map {
            it[SESSION_TOKEN]
        }?.asLiveData()
        tokenFlow?.observe(viewLifecycleOwner) {
            it?.let { token ->
                mainViewModel.dispatch(AuthAction.SetSessionTokenAction(token))
            }
        }
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                AuthNavigationGraph(
                    navController,
                    mainViewModel
                ) {
                    navigateToMainFragment()
                }
            }
        }
    }

    private fun navigateToMainFragment() {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            replace(R.id.nav_host_fragment, MainFragment())
        }
    }
}