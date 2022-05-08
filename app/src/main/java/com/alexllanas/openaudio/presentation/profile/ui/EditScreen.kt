package com.alexllanas.openaudio.presentation.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.GallerySelector
import com.alexllanas.openaudio.presentation.compose.components.SaveTopBar
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    onSettingsClick: () -> Unit
) {
    val mainState by mainViewModel.mainState.collectAsState()
    val homeState by homeViewModel.homeState.collectAsState()
    var name by remember { mutableStateOf(mainState.loggedInUser?.name ?: "username") }
    var location by remember { mutableStateOf(mainState.loggedInUser?.location ?: "location") }
    var bio by remember { mutableStateOf(mainState.loggedInUser?.bio ?: "bio") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            SaveTopBar("Edit Profile", onSaveAction = {
//                mainState.sessionToken?.let { token ->
//                    mainState.loggedInUser?.id?.let { userId ->
//                        homeViewModel.refreshSelectedUser(userId, token)
//                    }
//                }
                if (!homeState.isLoading) {
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
//                    PhotoPicker(mainViewModel)
                }
                Text(text = "Change Photo", modifier = Modifier.padding(top = 8.dp))
            }
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Information", modifier = Modifier, style = MaterialTheme.typography.h4)
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { name = it },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = name,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            mainState.sessionToken?.let { token ->
                                if (name != mainState.loggedInUser?.name) {
                                    mainViewModel.dispatch(
                                        ProfileAction.ChangeName(
                                            name,
                                            sessionToken = token
                                        )
                                    )
                                }
                            }
                        }
                    )
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { location = it },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = location,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            if (location != mainState.loggedInUser?.location) {
                                mainState.sessionToken?.let { token ->
                                    mainViewModel.dispatch(
                                        ProfileAction.ChangeLocation(
                                            location,
                                            sessionToken = token
                                        )
                                    )
                                }
                            }
                        }
                    )
                )
                TextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChange = { bio = it },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
                    value = bio,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            if (bio != mainState.loggedInUser?.bio) {
                                mainState.sessionToken?.let { token ->
                                    mainViewModel.dispatch(
                                        ProfileAction.ChangeBio(
                                            bio,
                                            sessionToken = token
                                        )
                                    )
                                }
                            }
                        }
                    )
                )
            }
        }
    }
}

