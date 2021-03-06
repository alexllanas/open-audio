package com.alexllanas.openaudio.presentation.common.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Queue
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.GreenTint
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.util.sendTweet
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TrackOptionsScreen(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    addToPlaylistRoute: String,
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize().padding(16.dp)
    ) {
        val (closeIcon, image, title, optionsMenu) = createRefs()

        Icon(
            imageVector = Icons.Default.Close,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(R.string.close),
            modifier = Modifier
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
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerHorizontallyTo(parent)
                }
        )
        OptionsMenu(modifier = Modifier.padding(8.dp).constrainAs(optionsMenu) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
        }, navHostController, homeViewModel, mainViewModel, addToPlaylistRoute, context)
    }
}

@Composable
fun OptionsMenu(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    addToPlaylistRoute: String,
    context: Context
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier.padding(top = 24.dp)) {
        Row(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
                .clickable(interactionSource = interactionSource, indication = null) {
                    homeState.selectedTrack?.id?.let { trackId ->
                        mainState.sessionToken?.let { token ->
                            mainState.loggedInUser?.id?.let { userId ->
                                homeViewModel.toggleTrackOptionsLike(trackId, token, userId)
//                                homeState.searchScreenState?.query?.let {
//                                    homeViewModel.dispatch(HomeAction.SearchAction(it))
//                                }
                            }
                        }
                    }
                }
        ) {
            if (homeState.selectedTrack?.liked == true) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = GreenTint,
                    contentDescription = stringResource(R.string.unfavorite)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = stringResource(R.string.favorite)
                )
            }
            Text(
                "Like",
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource, indication = null) {
                navHostController.navigate(addToPlaylistRoute)
            }
            .padding(bottom = 24.dp)) {
            Icon(
                imageVector = Icons.Outlined.Queue,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = stringResource(R.string.add_to_playlist)
            )
            Text(
                "Add To Playlist",
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding()
            .clickable(interactionSource = interactionSource, indication = null) {
                homeState.selectedTrack?.let { track ->
                    sendTweet(track, context)
                }
            }) {
            Icon(
                imageVector = Icons.Outlined.Share,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = stringResource(R.string.share)
            )
            Text(
                "Share",
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
