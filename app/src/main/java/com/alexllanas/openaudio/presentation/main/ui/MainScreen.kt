package com.alexllanas.openaudio.presentation.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
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
        }
    }
}