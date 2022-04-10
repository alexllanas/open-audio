package com.alexllanas.openaudio.presentation.home.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.common.ui.TrackList
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun StreamScreen(homeViewModel: HomeViewModel, mainViewModel: MainViewModel) {

    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Stream")
                })
        }
    ) {
        TrackList(
            homeState.stream,
            onHeartClick = { shouldLike, track ->
                homeViewModel.onHeartClick(
                    shouldLike,
                    track,
                    mainState.loggedInUser,
                    mainState.sessionToken
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    track: Track?,
    onTrackClick: (Track) -> Unit = {},
    onHeartClick: (Boolean, Track) -> Unit = { _: Boolean, _: Track -> },
    onMoreClick: (Track) -> Unit = {},
    isSelected: Boolean = false
) {
    val background = if (isSelected)
        Color.Red
    else
        MaterialTheme.colors.surface

    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .then(modifier),
        backgroundColor = background
    ) {
        val likesQualifier =
            if (track?.userLikeIds?.size == 1) " person likes this track" else " people like this track"
        ListItem(
            text = { Text(text = track?.title.toString(), maxLines = 1) },
            secondaryText = { Text(text = track?.userLikeIds?.size.toString() + likesQualifier) },
            icon = {
                GlideImage(
                    modifier = modifier.size(50.dp),
                    imageModel = track?.image,
                    contentScale = ContentScale.Crop,
                    placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                    error = ImageBitmap.imageResource(R.drawable.blank_user)
                )
            },
            trailing = {
                Row {
                    Log.d(TAG, "TrackItem: ${track?.liked}")
//                    if (track.liked) {
//                        Icon(
//                            Icons.Filled.Favorite,
//                            contentDescription = null,
//                            Modifier.clickable { onHeartClick(false, track) }
//                        )
//                    } else {
//                        Icon(
//                            Icons.Outlined.FavoriteBorder,
//                            contentDescription = null,
//                            Modifier.clickable { onHeartClick(true, track) }
//                        )
//
//                    }
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { track?.let { onMoreClick(it) } }
                            .padding(start = 4.dp)
                    )
                }
            },
            modifier = Modifier
                .clickable { track?.let { onTrackClick(it) } }
        )
    }
}



