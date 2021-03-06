package com.alexllanas.openaudio.presentation.home.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexllanas.openaudio.presentation.common.ui.AddToPlaylistScreen
import com.alexllanas.openaudio.presentation.common.ui.CreatePlaylistScreen
import com.alexllanas.openaudio.presentation.common.ui.TrackOptionsScreen
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel

sealed class TrackNavItem(
    var title: String,
    var icon: ImageVector? = null,
    var screenRoute: String
) {
    object TrackOptions : TrackNavItem("TrackOptions", null, "track_options")
    object TrackDetail : TrackNavItem("TrackDetail", null, "track_detail")
    object CreatePlaylist : TrackNavItem("CreatePlaylist", null, "create_playlist")
    object AddToPlaylist : TrackNavItem("AddToPlaylist", null, "add_to_playlist")

}

@Composable
fun TrackNavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    fragmentNavController: NavController
) {

    val mediaPlayerState by mediaPlayerViewModel.mediaPlayerState.collectAsState()

    NavHost(
        navController = navHostController,
        startDestination = TrackNavItem.TrackDetail.screenRoute
    ) {
        composable(TrackNavItem.TrackDetail.screenRoute) {
            TrackDetailScreen(
                homeViewModel,
                mainViewModel,
                mediaPlayerViewModel,
                fragmentNavController,
                navHostController
            )
        }
        composable(TrackNavItem.TrackOptions.screenRoute) {
            TrackOptionsScreen(
                homeViewModel,
                navHostController,
                mainViewModel,
                TrackNavItem.AddToPlaylist.screenRoute
            )
        }
        composable(TrackNavItem.CreatePlaylist.screenRoute) {
            CreatePlaylistScreen(navHostController, mainViewModel)
        }
        composable(TrackNavItem.AddToPlaylist.screenRoute) {
            AddToPlaylistScreen(
                mainViewModel,
                homeViewModel,
                mediaPlayerState,
                navHostController,
                TrackNavItem.CreatePlaylist.screenRoute
            )
        }
    }
}