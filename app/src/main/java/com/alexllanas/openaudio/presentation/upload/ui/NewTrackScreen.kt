package com.alexllanas.openaudio.presentation.upload.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.upload.state.UploadState
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NewTrackScreen(uploadViewModel: UploadViewModel) {
    val uploadState by uploadViewModel.uploadState.collectAsState()

    Scaffold(
        topBar = { NewTrackAppBar() }
    ) {
        ConstraintLayout(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            val (image, trackInfo) = createRefs()

            GlideImage(imageModel = uploadState.trackUrl,
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

            TrackDetail(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .constrainAs(trackInfo) {
                        start.linkTo(parent.start)
                        top.linkTo(image.bottom)
                    },
                uploadState
            )

        }

    }
}

@Composable
fun TrackDetail(
    modifier: Modifier = Modifier,
    uploadState: UploadState
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.weight(1f),
            text = uploadState.trackTitle + "track title",
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
            contentDescription = stringResource(R.string.like_track)
        )
    }
}

@Composable
fun NewTrackAppBar() {
    ConstraintLayout(
        Modifier.fillMaxWidth().height(height = 56.dp).background(MaterialTheme.colors.primary)
    ) {
        val (backArrow, title) = createRefs()

        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = stringResource(R.string.back_arrow),
            modifier = Modifier
                .alpha(0.8f)
                .clickable { }
                .padding(start = 16.dp)
                .constrainAs(backArrow) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                }
        )
        Text(
            text = "Upload",
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.constrainAs(title) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            },
            style = MaterialTheme.typography.h5
        )

    }
}