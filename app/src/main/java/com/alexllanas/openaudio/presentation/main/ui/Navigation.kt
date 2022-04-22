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
import androidx.compose.ui.draw.alpha
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
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.auth.ui.LandingScreen
import com.alexllanas.openaudio.presentation.auth.ui.LoginScreen
import com.alexllanas.openaudio.presentation.auth.ui.RegisterScreen
import com.alexllanas.openaudio.presentation.common.ui.*
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.ui.SearchScreen
import com.alexllanas.openaudio.presentation.home.ui.StreamScreen
import com.alexllanas.openaudio.presentation.common.ui.TrackOptionsScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import com.alexllanas.openaudio.presentation.models.UserUIModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.profile.ui.EditScreen
import com.alexllanas.openaudio.presentation.profile.ui.SettingsScreen
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import com.alexllanas.openaudio.presentation.upload.ui.NewTrackScreen
import com.alexllanas.openaudio.presentation.upload.ui.UploadScreen
import com.google.gson.Gson

sealed class NavItem(var title: String, var icon: ImageVector? = null, var screenRoute: String) {
    object Landing : NavItem("Landing", null, "landing")
    object Follow : NavItem("Follow", null, "follow/{title}")
    object NewTrack : NavItem("NewTrack", null, "new_track")
    object AddToPlaylist : NavItem("AddToPlaylist", null, "add_to_playlist")
    object TrackOptions : NavItem("TrackOptions", null, "track_options")
    object CreatePlaylist : NavItem("CreatePlaylist", null, "create_playlist")
    object Login : NavItem("Login", null, "login")
    object Register : NavItem("Register", null, "register")
    object Edit : NavItem("Edit", null, "edit")
    object Stream : NavItem("Stream", Icons.Filled.Home, "stream")
    object Search : NavItem("Search", Icons.Outlined.Search, "search")
    object Upload : NavItem("Upload", Icons.Filled.Upload, "upload")
    object Profile : NavItem("Profile", Icons.Filled.Person, "profile")
    object Settings : NavItem("Settings", null, "settings")
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
    mainViewModel: MainViewModel,
    authViewModel: AuthViewModel,
    uploadViewModel: UploadViewModel,
    profileViewModel: ProfileViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val homeState by homeViewModel.homeState.collectAsState()
    NavHost(
        navController = navHostController,
        startDestination = NavItem.Stream.screenRoute
    ) {
        composable(NavItem.Stream.screenRoute) {
//            AudioTestScreen(mainViewModel)
            MediaScreen(mainViewModel) {
                StreamScreen(homeViewModel, mainViewModel, navHostController)
            }
        }
        composable(
            NavItem.Follow.screenRoute,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            MediaScreen(mainViewModel) {
                FollowScreen(
                    navHostController,
                    homeViewModel,
                    mainState = mainState,
                    title ?: "No Title"
                )
            }
        }
        composable(NavItem.NewTrack.screenRoute) {
            MediaScreen(mainViewModel) {
                NewTrackScreen(uploadViewModel, navHostController, homeViewModel)
            }
        }
        composable(NavItem.TrackOptions.screenRoute) {
            TrackOptionsScreen(homeViewModel, navHostController, mainViewModel)
        }
        composable(NavItem.CreatePlaylist.screenRoute) {
            CreatePlaylistScreen(navHostController)
        }
        composable(NavItem.AddToPlaylist.screenRoute) {
            AddToPlaylistScreen(mainState, mainViewModel, homeViewModel, navHostController)
        }
        composable(NavItem.Settings.screenRoute) {
            MediaScreen(mainViewModel) {
                SettingsScreen(
                    profileViewModel = profileViewModel,
                    mainState = mainState,
                    navHostController
                )
            }
        }
        composable(NavItem.Login.screenRoute) {
            LoginScreen(authViewModel)
        }
        composable(NavItem.Edit.screenRoute) {
            MediaScreen(mainViewModel) {
                EditScreen(mainViewModel, navHostController = navHostController) {
                    navHostController.navigate(NavItem.Settings.screenRoute)
                }
            }
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
        }
        composable(NavItem.Search.screenRoute) {
            MediaScreen(mainViewModel) {
                SearchScreen(homeViewModel, mainViewModel, navHostController)
            }
        }
        composable(NavItem.Upload.screenRoute) {
            MediaScreen(mainViewModel) {
                UploadScreen(navController = navHostController, uploadViewModel)
            }
        }
        composable(NavItem.Profile.screenRoute) {
            MediaScreen(mainViewModel) {
                UserDetailScreen(
                    Modifier,
                    homeViewModel,
                    mainViewModel,
                    mainState,
                    navHostController,
                    true
                ) {
                    navHostController.navigate(NavItem.Edit.screenRoute)
                }
            }
        }
        composable(NavItem.UserDetail.screenRoute) {
            MediaScreen(mainViewModel) {
                UserDetailScreen(
                    Modifier,
                    homeViewModel,
                    mainViewModel,
                    mainState,
                    navHostController,
                    false
                ) {
                    navHostController.navigate(NavItem.Edit.screenRoute)
                }
            }
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
            MediaScreen(mainViewModel) {
                PlaylistDetailScreen(
                    modifier = Modifier,
                    mainState = mainState,
//                        playlist = playlist,
                    homeViewModel = homeViewModel,
                    navController = navHostController
                )
            }
//                }
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
        modifier = Modifier.alpha(0.97f),
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