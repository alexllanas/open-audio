import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.theme.GreenTint
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.state.MediaPlayerViewModel
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.models.TrackUIModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    mainState: MainState,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    playerViewModel: MediaPlayerViewModel,
    track: TrackUIModel?,
    onTrackClick: (TrackUIModel) -> Unit = {},
    onHeartClick: (TrackUIModel) -> Unit = { },
    onMoreClick: (TrackUIModel) -> Unit = {},
    isSelected: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    val mediaPlayerState by playerViewModel.mediaPlayerState.collectAsState()

    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        val likesQualifier =
            if (track?.userLikeIds?.size == 1) " person likes this track" else " people like this track"
        ListItem(
            text = {
                Text(
                    text = track?.title.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (mediaPlayerState.currentPlayingTrack?.id == track?.toDomain()?.id) GreenTint
                    else LocalContentColor.current.copy(
                        alpha = LocalContentAlpha.current
                    )
                )
            },
            secondaryText = {
                Text(text = track?.userLikeIds?.size.toString() + likesQualifier)
            },
//            icon = {
//                GlideImage(
//                    modifier = modifier.size(50.dp),
//                    imageModel = track?.image,
//                    contentScale = ContentScale.Crop,
//                    placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
//                    error = ImageBitmap.imageResource(R.drawable.blank_user)
//                )
//            },
            trailing = {
                Row {
                    track?.toDomain()?.let {
                        if (track.userLikeIds.contains(mainState.loggedInUser?.id)) {
                            track.liked = true
                        }
                        if (track.liked) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                tint = GreenTint,
                                contentDescription = stringResource(R.string.favorite),
                                modifier = Modifier.clickable { onHeartClick(track) }
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = stringResource(R.string.unfavorite),
                                modifier = Modifier.clickable { onHeartClick(track) }
                            )
                        }
                    }
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
                .clickable(interactionSource = interactionSource, indication = null) {
                    track?.let {
                        playerViewModel.dispatch(HomeAction.SetCurrentTrack(it.toDomain()))
                        homeViewModel.dispatch(HomeAction.SelectTrack(it.toDomain()))
                        it.mediaUrl?.let { videoId ->
                            playerViewModel.dispatch(HomeAction.SetVideoId(videoId))
                        }
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    }
                }
        )
    }
}

@Composable
fun TrackListItem(
    track: TrackUIModel?,
    mainState: MainState,
    onHeartClick: (TrackUIModel) -> Unit,
    onMoreClick: (TrackUIModel) -> Unit
) {
    val likesQualifier =
        if (track?.userLikeIds?.size == 1) " person likes this track" else " people like this track"
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(text = track?.title.toString(), maxLines = 1)
            Text(text = track?.userLikeIds?.size.toString() + likesQualifier)
        }
        Row {
            track?.toDomain()?.let {
                if (track.userLikeIds.contains(mainState.loggedInUser?.id)) {
                    track.liked = true
                }
                if (track.liked) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.favorite),
                        Modifier.clickable { onHeartClick(track) }
                    )
                } else {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.unfavorite),
                        Modifier.clickable { onHeartClick(track) }
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .clickable { track?.let { onMoreClick(it) } }
                    .padding(start = 4.dp)
            )
        }
    }
}
