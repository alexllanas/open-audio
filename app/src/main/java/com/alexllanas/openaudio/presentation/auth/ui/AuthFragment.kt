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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.compose.theme.OpenAudioTheme
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
        val fragmentNavController = activity?.findNavController(R.id.nav_host_fragment)

        return ComposeView(requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                OpenAudioTheme {
                    AuthNavigationGraph(
                        navController,
                        mainViewModel
                    ) {
                        fragmentNavController?.navigate(R.id.mainFragment)
//                        fragmentNavController?.navigate(AuthFragmentDirections.actionAuthFragmentToMainFragment())
                    }
                }
            }
        }
    }

}