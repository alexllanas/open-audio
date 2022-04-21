package com.alexllanas.openaudio.presentation.compose.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GallerySelector(mainViewModel: MainViewModel) {

    val mainState by mainViewModel.mainState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        Log.d(TAG, "GallerySelector: $uri")
        uri?.path?.let { path ->
            mainState.sessionToken?.let { token ->
                mainViewModel.dispatch(ProfileAction.ChangeAvatar(path, token))
            }
        }
    }
    AsyncImage(
        modifier = Modifier.size(160.dp).clip(CircleShape)
            .border(BorderStroke(1.dp, Color.Black), shape = CircleShape)
            .clickable {
                launcher.launch("image/*")
            },
        model =
        mainState.loggedInUser?.avatarUrl,
        contentDescription = null,
        placeholder = painterResource(R.drawable.blank_user),
        error = painterResource(R.drawable.blank_user),
        contentScale = ContentScale.Crop
    )
}