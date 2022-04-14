package com.alexllanas.openaudio.presentation.main.ui

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.NavItem.Landing.screenRoute

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    Scaffold(
//        bottomBar = {
//                BottomNav(navController = navController)
//        }
    ) {
        NavigationGraph(
            navHostController = navController,
            mainViewModel = mainViewModel,
            homeViewModel = homeViewModel,
            authViewModel = authViewModel
            )
    }
}