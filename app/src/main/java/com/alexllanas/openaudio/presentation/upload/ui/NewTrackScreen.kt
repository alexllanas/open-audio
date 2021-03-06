package com.alexllanas.openaudio.presentation.upload.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.appbars.TitleBackBar
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadAction
import com.alexllanas.openaudio.presentation.upload.state.UploadState
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NewTrackScreen(
    uploadViewModel: UploadViewModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val uploadState by uploadViewModel.uploadState.collectAsState()
    var newTrack = Track(title = "asdfasd")
    if (uploadState.uploadedTrackResults.isNotEmpty()) {
        newTrack = uploadState.uploadedTrackResults[0]
    }
    newTrack.mediaUrl?.let { url ->
        newTrack.title?.let { title ->
            mainState.sessionToken?.let { token ->
                LaunchedEffect(Unit) {
                    uploadViewModel.dispatch(
                        UploadAction.UploadTrack(
                            url,
                            title,
                            newTrack.image ?: "",
                            token
                        )
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = { TitleBackBar("Upload") { navHostController.popBackStack() } },
        bottomBar = { BottomNav(navController = navHostController) }
    ) {
        ConstraintLayout(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val (image, trackInfo) = createRefs()

            GlideImage(imageModel = newTrack.image,
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

            TrackInfo(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .constrainAs(trackInfo) {
                        start.linkTo(parent.start)
                        top.linkTo(image.bottom)
                    },
                homeViewModel = homeViewModel,
                uploadState = uploadState,
                navHostController = navHostController,
                newTrack = newTrack
            )
        }

    }
}

@Composable
fun TrackInfo(
    modifier: Modifier = Modifier,
    uploadState: UploadState,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    newTrack: Track
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = newTrack.title ?: "No Title",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Start
        )
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(R.string.like_track)
        )
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = stringResource(R.string.like_track),
            modifier = Modifier.clickable {
                homeViewModel.dispatch(HomeAction.SelectTrack(newTrack))
                navHostController.navigate("track_options")
            }
        )
    }
}
