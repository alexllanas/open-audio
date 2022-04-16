package com.alexllanas.openaudio.presentation.main.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.common.ui.MediaPlayerControls
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.NavItem.Landing.screenRoute
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    uploadViewModel: UploadViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    Scaffold(
//        bottomBar = {
//                BottomNav(navController = navController)
//        }
    ) {
        Box {
            NavigationGraph(
                navHostController = navController,
                mainViewModel = mainViewModel,
                homeViewModel = homeViewModel,
                authViewModel = authViewModel,
                uploadViewModel = uploadViewModel,
                profileViewModel = profileViewModel
            )
//            MediaPlayerControls(
//                mainViewModel = mainViewModel,
//                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp)
//            )
        }
    }
}