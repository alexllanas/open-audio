package com.alexllanas.openaudio.presentation.audio.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.common.ui.MediaPlayerControls

@Composable
fun MediaScreen(
    mainViewModel: MainViewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    fragmentManager: FragmentManager?,
    fragmentNavController: NavController,
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier) {
        content()
        MediaPlayerControls(
            mainViewModel = mainViewModel,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 64.dp),
            mediaPlayerViewModel = mediaPlayerViewModel,
            fragmentManager = fragmentManager,
            fragmentNavController = fragmentNavController
        )
    }
}