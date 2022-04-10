package com.alexllanas.openaudio.presentation.main.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel, homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(navController = navController) }
    ) {
        NavigationGraph(
            navHostController = navController,
            mainViewModel = mainViewModel,
            homeViewModel = homeViewModel
        )
    }
}