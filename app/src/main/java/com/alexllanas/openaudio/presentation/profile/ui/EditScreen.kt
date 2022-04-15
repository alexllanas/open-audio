package com.alexllanas.openaudio.presentation.profile.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.SaveTopBar
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun EditScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    onSettingsClick: () -> Unit
) {
    val mainState by mainViewModel.mainState.collectAsState()
    Scaffold(
        topBar = {
            SaveTopBar("Edit Profile", {}, true, onSettingsClick)
        },
        bottomBar = { BottomNav(navController = navHostController) }
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())) {
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

