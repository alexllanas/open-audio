package com.alexllanas.openaudio.presentation.main.ui

import android.os.Bundle
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.auth.ui.LandingScreen
import com.alexllanas.openaudio.presentation.auth.ui.LoginScreen
import com.alexllanas.openaudio.presentation.auth.ui.RegisterScreen
import com.alexllanas.openaudio.presentation.common.ui.PlaylistDetailScreen
import com.alexllanas.openaudio.presentation.common.ui.UserDetailScreen
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.ui.SearchScreen
import com.alexllanas.openaudio.presentation.home.ui.StreamScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.alexllanas.openaudio.presentation.models.UserUIModel
import com.alexllanas.openaudio.presentation.profile.ui.ProfileScreen
import com.alexllanas.openaudio.presentation.upload.ui.UploadScreen
import com.google.gson.Gson

sealed class NavItem(var title: String, var icon: ImageVector? = null, var screenRoute: String) {
    object Landing : NavItem("Landing", null, "landing")
    object Login : NavItem("Login", null, "login")
    object Register : NavItem("Register", null, "register")
    object Stream : NavItem("Stream", Icons.Filled.Home, "stream")
    object Search : NavItem("Search", Icons.Outlined.Search, "search")
    object Upload : NavItem("Upload", Icons.Filled.Upload, "upload")
    object Profile : NavItem("Profile", Icons.Filled.Person, "profile")
    object PlaylistDetail :
        NavItem("PlaylistDetail", null, "playlist_detail")
//        NavItem("PlaylistDetail", null, "playlist_detail/{playlistUIModel}")

    object UserDetail :
        NavItem("UserDetail", null, "user_detail")
}

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()
    NavHost(
        navController = navHostController,
        startDestination = NavItem.Landing.screenRoute
    ) {
        composable(NavItem.Stream.screenRoute) {
            StreamScreen(homeViewModel, mainViewModel, navHostController)
        }
        composable(NavItem.Login.screenRoute) {
            LoginScreen()
        }
        composable(NavItem.Register.screenRoute) {
            RegisterScreen()
        }
        composable(NavItem.Landing.screenRoute) {
            LandingScreen(
                {
                    navHostController.navigate(NavItem.Register.screenRoute)
                },
                {
                    navHostController.navigate(NavItem.Login.screenRoute)
                }
            )
            composable(NavItem.Search.screenRoute) {
                SearchScreen(homeViewModel, mainViewModel, navHostController)
            }
            composable(NavItem.Upload.screenRoute) {
                UploadScreen()
            }
            composable(NavItem.Profile.screenRoute) {
                ProfileScreen()
            }
            composable(NavItem.UserDetail.screenRoute) {
                UserDetailScreen(Modifier, homeViewModel, mainState, navHostController)
            }
            composable(
                NavItem.PlaylistDetail.screenRoute,
//            arguments = listOf(
//                navArgument("playlistUIModel") {
//                    type = PlaylistUIModelType()
//                }
//            )
            ) { backStackEntry ->
//            backStackEntry.arguments?.getParcelable<PlaylistUIModel>("playlistUIModel")
//                ?.let { playlist ->
                PlaylistDetailScreen(
                    modifier = Modifier,
                    mainState = mainState,
//                        playlist = playlist,
                    homeViewModel = homeViewModel,
                    navController = navHostController
                )
//                }
            }
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        NavItem.Stream,
        NavItem.Search,
        NavItem.Upload,
        NavItem.Profile
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(text = item.title, fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    Log.d(TAG, "BottomNav: ${navController.currentDestination}")
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

class PlaylistUIModelType : NavType<PlaylistUIModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PlaylistUIModel? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): PlaylistUIModel {
        val g = Gson().fromJson(value, PlaylistUIModel::class.java)
        Log.d(TAG, "parseValue: $g")
        return g
    }

    override fun put(bundle: Bundle, key: String, value: PlaylistUIModel) {
        bundle.putParcelable(key, value)
    }
}

class UserUIModelType : NavType<UserUIModel>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): UserUIModel? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): UserUIModel {
        return Gson().fromJson(value, UserUIModel::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: UserUIModel) {
        bundle.putParcelable(key, value)
    }

}

fun navigateToPlaylistDetail(
    navHostController: NavHostController
) {
    navHostController.navigate("playlist_detail")
}

fun navigateToUserDetail(
    navHostController: NavHostController
) {
    navHostController.navigate("user_detail")
}