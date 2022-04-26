package com.alexllanas.openaudio.presentation.profile.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
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
import com.alexllanas.openaudio.presentation.compose.components.GallerySelector
import com.alexllanas.openaudio.presentation.compose.components.SaveTopBar
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import com.skydoves.landscapist.glide.GlideImage
import org.schabi.newpipe.extractor.timeago.patterns.id
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun EditScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    onSettingsClick: () -> Unit
) {
    val mainState by mainViewModel.mainState.collectAsState()
    var name by remember { mutableStateOf(mainState.loggedInUser?.name ?: "username") }
    var location by remember { mutableStateOf(mainState.loggedInUser?.location ?: "location") }
    var bio by remember { mutableStateOf(mainState.loggedInUser?.bio ?: "bio") }

    Scaffold(
        topBar = {
            SaveTopBar("Edit Profile", {
                mainState.sessionToken?.let { token ->
                    if (name != mainState.loggedInUser?.name) {
                        mainViewModel.dispatch(ProfileAction.ChangeName(name, sessionToken = token))
                    }
                    if (location != mainState.loggedInUser?.location) {
                        mainViewModel.dispatch(
                            ProfileAction.ChangeLocation(
                                location,
                                sessionToken = token
                            )
                        )
                    }
                    if (bio != mainState.loggedInUser?.bio) {
                        mainViewModel.dispatch(ProfileAction.ChangeBio(bio, sessionToken = token))
                    }
                    mainState.loggedInUser?.id?.let { userId ->
                        homeViewModel.refreshSelectedUser(userId, token)
                    }

                }
                if (!mainState.isLoading) {
                    navHostController.popBackStack()
                }
            }, true, onSettingsClick)
        },
        bottomBar = { BottomNav(navController = navHostController) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    GallerySelector(mainViewModel)
                }
                Text(text = "Change Photo", modifier = Modifier.padding(top = 8.dp))
            }
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Information", modifier = Modifier, style = MaterialTheme.typography.h4)
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { name = it },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = name
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { location = it },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = location
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { bio = it },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = bio
                )
            }
        }
    }
}

