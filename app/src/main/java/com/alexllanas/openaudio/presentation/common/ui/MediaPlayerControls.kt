package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MediaPlayerControls(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val mainState by mainViewModel.mainState.collectAsState()

    Card(
        modifier = modifier.padding(8.dp).height(64.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            GlideImage(
                modifier = Modifier.size(50.dp),
                imageModel = mainState.currentPlayingTrack?.image,
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
            Text(
                text = mainState.currentPlayingTrack?.title ?: "Track Title",
                modifier = Modifier.weight(1f).padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterVertically),
                imageVector = Icons.Default.PlayCircle,
                contentDescription = stringResource(R.string.play_pause_button)
            )
        }
    }
}