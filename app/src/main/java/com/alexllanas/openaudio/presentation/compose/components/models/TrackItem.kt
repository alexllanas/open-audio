import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.alexllanas.openaudio.presentation.models.TrackUIModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    mainState: MainState,
    mainViewModel: MainViewModel,
    track: TrackUIModel?,
    onTrackClick: (TrackUIModel) -> Unit = {},
    onHeartClick: (TrackUIModel) -> Unit = { },
    onMoreClick: (TrackUIModel) -> Unit = {},
    isSelected: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val background = if (isSelected)
        Color.LightGray
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
            },
            modifier = Modifier
                .clickable {
                    track?.let {
                        mainViewModel.dispatch(HomeAction.SetCurrentTrack(it.toDomain()))
                        keyboardController?.hide()
                        focusManager.clearFocus(true)
                    }
                }
        )
    }
}

