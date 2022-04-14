package com.alexllanas.openaudio.presentation.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun EditScreen(mainViewModel: MainViewModel) {
    val mainState by mainViewModel.mainState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Profile")
                },
                navigationIcon = {
                    Text("Save", modifier = Modifier.padding(8.dp))
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = stringResource(R.string.back_arrow)
//                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                ) {
                    GlideImage(
                        modifier = Modifier.size(160.dp)
                            .clip(CircleShape)
                            .border(BorderStroke(1.dp, Color.Black), shape = CircleShape),
                        imageModel = URLDecoder.decode(
                            mainState.loggedInUser?.avatarUrl,
                            StandardCharsets.UTF_8.toString()
                        ),
                        contentScale = ContentScale.Crop,
                        placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                        error = ImageBitmap.imageResource(R.drawable.blank_user)
                    )
                }
                Text(text = "Change Photo", modifier = Modifier.padding(top = 8.dp))
            }
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Information", modifier = Modifier, style = MaterialTheme.typography.h4)
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = mainState.loggedInUser?.name ?: "username"
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = mainState.loggedInUser?.location ?: "location"
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = mainState.loggedInUser?.bio ?: "biography"
                )
            }
        }
    }
}
