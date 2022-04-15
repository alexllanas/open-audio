package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.R

@Composable
fun AddToPlaylistScreen() {
    var playlistName by remember { mutableStateOf("") }

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
            onValueChange = { playlistName = it },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(playlistNameInputField) {
                    top.linkTo(promptText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
        Text(
            text = stringResource(R.string.cancel),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
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
                .padding(top = 64.dp)
                .constrainAs(skipOption) {
                    top.linkTo(playlistNameInputField.bottom)
                    start.linkTo(cancelOption.end)
                    end.linkTo(parent.end)
                }
        )

    }
}