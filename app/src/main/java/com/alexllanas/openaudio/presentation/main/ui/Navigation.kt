package com.alexllanas.openaudio.presentation.main.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexllanas.openaudio.presentation.auth.ForgotPasswordScreen
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.auth.ui.LandingScreen
import com.alexllanas.openaudio.presentation.auth.ui.RegisterScreen
import com.alexllanas.openaudio.presentation.common.ui.*
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.home.ui.SearchScreen
import com.alexllanas.openaudio.presentation.home.ui.StreamScreen
import com.alexllanas.openaudio.presentation.common.ui.TrackOptionsScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.profile.ui.EditScreen
import com.alexllanas.openaudio.presentation.profile.ui.SettingsScreen
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import com.alexllanas.openaudio.presentation.upload.ui.NewTrackScreen
import com.alexllanas.openaudio.presentation.upload.ui.UploadScreen

sealed class NavItem(var title: String, var icon: ImageVector? = null, var screenRoute: String) {
    object Landing : NavItem("Landing", null, "landing")
    object ForgotPassword : NavItem("ForgotPassword", null, "forgot_password")
    object Login : NavItem("Login", null, "login")
    object Register : NavItem("Register", null, "register")
    object Follow : NavItem("Follow", null, "follow/{title}")
    object ProfileFollow : NavItem("ProfileFollow", null, "profile_follow/{title}")
    object NewTrack : NavItem("NewTrack", null, "new_track")
    object AddToPlaylist : NavItem("AddToPlaylist", null, "add_to_playlist")
    object TrackOptions : NavItem("TrackOptions", null, "track_options")
    object CreatePlaylist : NavItem("CreatePlaylist", null, "create_playlist")
    object Edit : NavItem("Edit", null, "edit")
    object Stream : NavItem("Stream", Icons.Filled.Home, "stream")
    object Search : NavItem("Search", Icons.Outlined.Search, "search")
    object Upload : NavItem("Upload", Icons.Filled.Upload, "upload")
    object Profile : NavItem("Profile", Icons.Filled.Person, "profile")
    object Settings : NavItem("Settings", null, "settings")
    object PlaylistDetail :
        NavItem("PlaylistDetail", null, "playlist_detail")

    object ProfilePlaylist : NavItem("ProfilePlaylist", null, "profile_playlist")
    object ProfileUserPlaylist : NavItem("ProfileUserPlaylist", null, "profile_user_playlist")

    object UserDetail :
        NavItem("UserDetail", null, "user_detail")

    object ProfileUser :
        NavItem("ProfileUser", null, "profile_user")
}

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    playerViewModel: MediaPlayerViewModel,
    authViewModel: AuthViewModel,
    uploadViewModel: UploadViewModel,
    profileViewModel: ProfileViewModel,
    fragmentManager: FragmentManager?,
    fragmentNavController: NavController
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val mediaPlayerState by playerViewModel.mediaPlayerState.collectAsState()
    NavHost(
        navController = navHostController,
        startDestination = NavItem.Stream.screenRoute
    ) {
        composable(NavItem.Stream.screenRoute) {
//            MediaScreen(mainViewModel) {
            StreamScreen(homeViewModel, mainViewModel, playerViewModel, navHostController)
//            }
        }
        composable(
            NavItem.Follow.screenRoute,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
//            MediaScreen(mainViewModel) {
            FollowScreen(
                navHostController,
                homeViewModel,
                mediaPlayerState,
                mainState = mainState,
                title ?: "No Title",
            )
//            }
        }
        composable(
            NavItem.ProfileFollow.screenRoute,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
//            MediaScreen(mainViewModel) {
            ProfileFollowScreen(
                navHostController,
                homeViewModel,
                mediaPlayerState,
                mainState = mainState,
                title ?: "No Title",
            )
//            }
        }
        composable(NavItem.NewTrack.screenRoute) {
//            MediaScreen(mainViewModel) {
            NewTrackScreen(uploadViewModel, mainViewModel, navHostController, homeViewModel)
//            }
        }
        composable(NavItem.ForgotPassword.screenRoute) {
            ForgotPasswordScreen(navHostController)
        }
        composable(NavItem.TrackOptions.screenRoute) {
            TrackOptionsScreen(
                homeViewModel,
                navHostController,
                mainViewModel,
                NavItem.AddToPlaylist.screenRoute
            )
        }
        composable(NavItem.CreatePlaylist.screenRoute) {
            CreatePlaylistScreen(navHostController, mainViewModel)
        }
        composable(NavItem.AddToPlaylist.screenRoute) {
            AddToPlaylistScreen(
                mainViewModel,
                homeViewModel,
                mediaPlayerState,
                navHostController,
                NavItem.CreatePlaylist.screenRoute
            )
        }
        composable(NavItem.Settings.screenRoute) {
//            MediaScreen(mainViewModel) {
            SettingsScreen(
                mainViewModel = mainViewModel,
                homeViewModel = homeViewModel,
                mediaPlayerViewModel = playerViewModel,
                profileViewModel = profileViewModel,
                mainState = mainState,
                navHostController = navHostController,
                fragmentManager = fragmentManager,
                fragmentNavController = fragmentNavController
            )
//            }
        }
        composable(NavItem.Login.screenRoute) {
//            LoginScreen(mainViewModel, navController = navHostController)
        }
        composable(NavItem.Edit.screenRoute) {
//            MediaScreen(mainViewModel) {
            EditScreen(
                mainViewModel,
                navHostController = navHostController,
                homeViewModel = homeViewModel
            ) {
                navHostController.navigate(NavItem.Settings.screenRoute)
            }
//            }
        }
        composable(NavItem.Register.screenRoute) {
            RegisterScreen(navHostController)
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
//            MediaScreen(mainViewModel) {
            SearchScreen(homeViewModel, mainViewModel, playerViewModel, navHostController)
//            }
        }
        composable(NavItem.Upload.screenRoute) {
//            MediaScreen(mainViewModel) {
            UploadScreen(navController = navHostController, uploadViewModel)
//            }
        }
        composable(NavItem.Profile.screenRoute) {
//            MediaScreen(mainViewModel) {
            ProfileDetailScreen(
                Modifier,
                homeViewModel,
                mainViewModel,
                mainState,
                mediaPlayerState,
                navHostController,
            ) {
                navHostController.navigate(NavItem.Edit.screenRoute)
            }
//            }
        }
        composable(NavItem.UserDetail.screenRoute) {
//            MediaScreen(mainViewModel) {
            UserDetailScreen(
                Modifier,
                homeViewModel,
                mainViewModel,
                mainState,
                mediaPlayerState,
                navHostController
            ) {
                navHostController.navigate(NavItem.Edit.screenRoute)
            }
//            }
        }
        composable(NavItem.ProfileUser.screenRoute) {
//            MediaScreen(mainViewModel) {
            ProfileUserScreen(
                Modifier,
                homeViewModel,
                mainViewModel,
                mainState,
                mediaPlayerState,
                navHostController
            )
//            }
        }
        composable(
            NavItem.PlaylistDetail.screenRoute,
        ) {
//            MediaScreen(mainViewModel) {
            PlaylistDetailScreen(
                modifier = Modifier,
                mainState = mainState,
                homeViewModel = homeViewModel,
                playerViewModel = playerViewModel,
                navController = navHostController,
                mainViewModel = mainViewModel,
            )
        }
        composable(NavItem.ProfilePlaylist.screenRoute) {
            ProfilePlaylistDetailScreen(
                modifier = Modifier,
                mainState = mainState,
                homeViewModel = homeViewModel,
                playerViewModel = playerViewModel,
                navController = navHostController,
                mainViewModel = mainViewModel,
            )
        }
        composable(NavItem.ProfileUserPlaylist.screenRoute) {
            ProfileUserPlaylistScreen(
                modifier = Modifier,
                mainState = mainState,
                homeViewModel = homeViewModel,
                playerViewModel = playerViewModel,
                navController = navHostController,
                mainViewModel = mainViewModel,
            )
        }

    }
}
