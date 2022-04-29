package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.main.state.MainViewModel

@Composable
fun CreatePlaylistScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    var playlistName by remember { mutableStateOf("") }
    val mainState by mainViewModel.mainState.collectAsState()

    ConstraintLayout(Modifier.fillMaxSize().padding(16.dp)) {
        val (promptText, playlistNameInputField, cancelOption, skipOption) = createRefs()
        Text(
            text = stringResource(R.string.give_playlist_name),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(top = 96.dp)
                .constrainAs(promptText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        TextField(
            value = playlistName,
            singleLine = true,
            onValueChange = { playlistName = it },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(playlistNameInputField) {
                    top.linkTo(promptText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            textStyle = LocalTextStyle.current
                .copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    mainState.loggedInUser?.let { user ->
                        mainState.sessionToken?.let { token ->
                            mainViewModel.dispatch(
                                HomeAction.CreatePlaylist(
                                    playlistName,
                                    user,
                                    token
                                )
                            )
                            navHostController.popBackStack()
                        }
                    }
                }
            )
        )
        Text(
            text = stringResource(R.string.cancel),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .clickable {
                    navHostController.popBackStack()
                }
                .padding(top = 64.dp)
                .constrainAs(cancelOption) {
                    top.linkTo(playlistNameInputField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(skipOption.start)
                }
        )
        Text(
            text = stringResource(R.string.skip),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .clickable {
                    mainState.loggedInUser?.let { user ->
                        mainState.sessionToken?.let { token ->
                            mainViewModel.dispatch(
                                HomeAction.CreatePlaylist(
                                    user = user,
                                    sessionToken = token
                                )
                            )
                            navHostController.popBackStack()
                        }
                    }
                }
                .padding(top = 64.dp)
                .constrainAs(skipOption) {
                    top.linkTo(playlistNameInputField.bottom)
                    start.linkTo(cancelOption.end)
                    end.linkTo(parent.end)
                }
        )
    }
}