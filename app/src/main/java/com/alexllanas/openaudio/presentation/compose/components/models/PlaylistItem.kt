import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    playlist: Playlist?,
    onPlaylistClick: (Playlist) -> Unit = {},
    isSelected: Boolean = false,
    countVisible: Boolean
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        elevation = 0.dp
    ) {
        ListItem(
            text = { Text(text = playlist?.name.toString(), maxLines = 1) },
            secondaryText = {
                if (countVisible) {
                    playlist?.trackCount?.let { count ->
                        Text(text = "$count tracks")
                    }
                }
            },
//            icon = {
//                GlideImage(
//                    modifier = modifier.size(50.dp),
//                    imageModel = playlist?.coverImage,
//                    contentScale = ContentScale.Crop,
//                    placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
//                    error = ImageBitmap.imageResource(R.drawable.blank_user)
//                )
//            },
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .clickable {
                    playlist?.let {
                        onPlaylistClick(it)
                    }
                }
        )
    }
}

