package com.alexllanas.openaudio.presentation.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.rememberNavController
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.common.ui.MediaScreen
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    uploadViewModel: UploadViewModel,
    profileViewModel: ProfileViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    fragmentManager: FragmentManager?
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) {

        Box {
            MediaScreen(mainViewModel, mediaPlayerViewModel) {
                NavigationGraph(
                    navHostController = navController,
                    mainViewModel = mainViewModel,
                    playerViewModel = mediaPlayerViewModel,
                    homeViewModel = homeViewModel,
                    authViewModel = authViewModel,
                    uploadViewModel = uploadViewModel,
                    profileViewModel = profileViewModel,
                    fragmentManager = fragmentManager
                )
            }
        }
    }
}