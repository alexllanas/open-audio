package com.alexllanas.openaudio.presentation.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel

class TrackDetailFragment : Fragment() {

    val homeViewModel: HomeViewModel by activityViewModels()
    val mainViewModel: MainViewModel by activityViewModels()
    val mediaPlayerViewModel: MediaPlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        onBackPressed()

        return ComposeView(requireContext()).apply {

            setContent {
                TrackDetailScreen(
                    homeViewModel,
                    mainViewModel,
                    mediaPlayerViewModel = mediaPlayerViewModel
                )
            }
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}