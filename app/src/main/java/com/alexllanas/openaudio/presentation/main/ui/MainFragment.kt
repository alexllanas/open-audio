package com.alexllanas.openaudio.presentation.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import java.lang.IllegalArgumentException

class MainFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val uploadViewModel: UploadViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val mediaPlayerViewModel: MediaPlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onBackPressed()


        val fragmentNavController = activity?.findNavController(R.id.nav_host_fragment)

        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen(
                    homeViewModel = homeViewModel,
                    mainViewModel = mainViewModel,
                    authViewModel = authViewModel,
                    uploadViewModel = uploadViewModel,
                    profileViewModel = profileViewModel,
                    mediaPlayerViewModel = mediaPlayerViewModel,
                    fragmentManager = activity?.supportFragmentManager,
                    fragmentNavController = fragmentNavController?: throw IllegalArgumentException("Cannot find NavController.")
                )
            }
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(false)
            }
        })
    }
}