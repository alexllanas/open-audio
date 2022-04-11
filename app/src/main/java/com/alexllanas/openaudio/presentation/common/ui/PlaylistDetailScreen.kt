package com.alexllanas.openaudio.presentation.common.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PlaylistDetailScreen(
    modifier: Modifier = Modifier,
    playlist: PlaylistUIModel,
    homeViewModel: HomeViewModel,
    mainState: MainState
) {
    val homeState by homeViewModel.homeState.collectAsState()
    Log.d(TAG, "PlaylistDetailScreen: ${playlist.url}")
    homeViewModel.dispatch(
        HomeAction.GetPlaylistTracks(
            playlistUrl = playlist.url
        )
    )

    Scaffold(topBar = {
        TopAppBar {
            Row(modifier = modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                        R.string.back_arrow
                    )
                )
            }
        }
    }) {
        Column(modifier = Modifier) {
            Header(modifier, playlist)
            TrackList(tracks = homeState.selectedPlaylistTracks.toUI(),
                onHeartClick = { shouldLike, track ->
                    homeViewModel.onHeartClick(
                        shouldLike,
                        track.toDomain(),
                        mainState.loggedInUser,
                        mainState.sessionToken
                    )
                },
                onMoreClick = {})
        }

    }
}

@Composable
fun Header(modifier: Modifier = Modifier, playlist: PlaylistUIModel) {
    Column {
        GlideImage(
            modifier = Modifier.size(100.dp),
            imageModel = URLDecoder.decode(playlist.coverImage, StandardCharsets.UTF_8.toString()),
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
            error = ImageBitmap.imageResource(R.drawable.blank_user)
        )
        Text(text = playlist.name.toString(), style = MaterialTheme.typography.h5)
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                imageModel = URLDecoder.decode(
                    playlist.author?.avatarUrl,
                    StandardCharsets.UTF_8.toString()
                ),
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
            Text(text = playlist.author?.name.toString(), style = MaterialTheme.typography.body1)
        }
    }
}
