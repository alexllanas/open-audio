package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.presentation.main.state.MainViewModel

@Composable
fun MediaScreen(mainViewModel: MainViewModel, content: @Composable (() -> Unit)) {
    Box {
        content()
        MediaPlayerControls(
            mainViewModel = mainViewModel,
            modifier = Modifier.align(Alignment.TopCenter).padding(bottom = 64.dp)
        )
    }
}