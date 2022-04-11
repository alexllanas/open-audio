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
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.lists.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.PlaylistUIModel
import com.alexllanas.openaudio.presentation.models.TrackUIList
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlaylistDetailScreen(
    modifier: Modifier = Modifier,
    playlist: PlaylistUIModel,
    homeViewModel: HomeViewModel,
    mainState: MainState
) {
    val homeState by homeViewModel.homeState.collectAsState()

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
        Column(modifier = modifier.fillMaxSize()) {
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
        Log.d(TAG, "Header: $playlist")
        GlideImage(
            modifier = Modifier,
            imageModel = playlist.coverImage,
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
            error = ImageBitmap.imageResource(R.drawable.blank_user)
        )
        Text(text = playlist.name.toString(), style = MaterialTheme.typography.h5)
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                modifier = modifier.size(50.dp).clip(CircleShape),
                imageModel = playlist.author?.avatarUrl,
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
            Text(text = playlist.author?.name.toString(), style = MaterialTheme.typography.body1)
        }
    }
}
