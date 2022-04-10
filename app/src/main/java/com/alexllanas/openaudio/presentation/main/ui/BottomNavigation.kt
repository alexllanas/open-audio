package com.alexllanas.openaudio.presentation.main.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.ui.SearchScreen
import com.alexllanas.openaudio.presentation.home.ui.StreamScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.profile.ui.ProfileScreen
import com.alexllanas.openaudio.presentation.upload.ui.UploadScreen

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screenRoute: String) {
    object Stream : BottomNavItem("Stream", Icons.Filled.Home, "stream")
    object Search : BottomNavItem("Search", Icons.Outlined.Search, "search")
    object Upload : BottomNavItem("Upload", Icons.Filled.Upload, "upload")
    object Profile : BottomNavItem("Profile", Icons.Filled.Person, "profile")
}

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavItem.Stream.screenRoute
    ) {
        composable(BottomNavItem.Stream.screenRoute) {
            StreamScreen(homeViewModel, mainViewModel)
        }
        composable(BottomNavItem.Search.screenRoute) {
            SearchScreen(homeViewModel, mainViewModel)
        }
        composable(BottomNavItem.Upload.screenRoute) {
            UploadScreen()
        }
        composable(BottomNavItem.Profile.screenRoute) {
            ProfileScreen()
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItem.Stream,
        BottomNavItem.Search,
        BottomNavItem.Upload,
        BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}