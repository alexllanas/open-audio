package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Queue
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TrackOptionsScreen(homeViewModel: HomeViewModel, navHostController: NavHostController) {
    val homeState by homeViewModel.homeState.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (closeIcon, image, title, optionsMenu) = createRefs()

        Icon(
            imageVector = Icons.Default.Close, contentDescription = stringResource(R.string.close),
            Modifier
                .clickable { navHostController.popBackStack() }
                .constrainAs(closeIcon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        GlideImage(imageModel = homeState.selectedTrack?.image,
            contentScale = ContentScale.Crop,
            placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
            error = ImageBitmap.imageResource(R.drawable.blank_user),
            modifier = Modifier
                .padding(top = 64.dp)
                .size(160.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                })
        Text(
            homeState.selectedTrack?.title ?: "Track Title",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        OptionsMenu(modifier = Modifier.padding(8.dp).constrainAs(optionsMenu) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
        }, navHostController)
    }
}

@Composable
fun OptionsMenu(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Column(modifier) {
        Row(modifier = Modifier.padding(bottom = 24.dp).fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.like_track)
            )
            Text(
                "Like",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navHostController.navigate("add_to_playlist")
            }
            .padding(bottom = 24.dp)) {
            Icon(
                imageVector = Icons.Outlined.Queue,
                contentDescription = stringResource(R.string.add_to_playlist)
            )
            Text(
                "Add To Playlist",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding()) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = stringResource(R.string.share)
            )
            Text(
                "Share",
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
