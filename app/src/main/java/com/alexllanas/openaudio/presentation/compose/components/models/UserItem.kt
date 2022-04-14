import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.R
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User?,
    onUserClick: (User) -> Unit = {},
    onFollowClick: (Boolean, User) -> Unit = { _: Boolean, _: User -> },
    isSelected: Boolean = false
) {
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
        ListItem(
            text = { Text(text = user?.name.toString(), maxLines = 1) },
            secondaryText = { Text(text = "${user?.lastTrack?.title}") },
            icon = {
                GlideImage(
                    modifier = modifier.size(50.dp).clip(CircleShape)
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            shape = CircleShape,
                        ),
                    imageModel = user?.avatarUrl,
                    contentScale = ContentScale.Crop,
                    placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                    error = ImageBitmap.imageResource(R.drawable.blank_user)
                )
            },
            trailing = {
                Row {
                    if (user?.isSubscribing == true) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = stringResource(R.string.unfollow),
                            Modifier.clickable { onFollowClick(false, user) }
                        )
                    } else {
                        Icon(
                            Icons.Outlined.PersonAdd,
                            contentDescription = stringResource(R.string.follow),
                            Modifier.clickable {
                                user?.let {
                                    onFollowClick(true, user)
                                }
                            }
                        )

                    }
                }
            },
            modifier = Modifier
                .clickable { user?.let { onUserClick(it) } }
        )
    }
}

