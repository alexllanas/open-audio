package com.alexllanas.openaudio.presentation.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.OpenAudioTheme
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import java.lang.IllegalArgumentException

class TrackDetailFragment : Fragment() {

    val homeViewModel: HomeViewModel by activityViewModels()
    val mainViewModel: MainViewModel by activityViewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by activityViewModels()
    var fragmentNavController: NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentNavController = activity?.findNavController(R.id.nav_host_fragment)

        onBackPressed()

        return ComposeView(requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                OpenAudioTheme {
                    TrackNavigationGraph(
                        navController,
                        mainViewModel,
                        homeViewModel,
                        mediaPlayerViewModel,
                        fragmentNavController
                            ?: throw IllegalArgumentException("Fragment NavController is null.")
                    )
                }
            }
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentNavController?.popBackStack()
            }
        })
    }
}