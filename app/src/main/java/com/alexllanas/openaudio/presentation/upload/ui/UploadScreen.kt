package com.alexllanas.openaudio.presentation.upload.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.DefaultButton
import com.alexllanas.openaudio.presentation.upload.state.UploadAction
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel

@Composable
fun UploadScreen(navController: NavHostController, uploadViewModel: UploadViewModel) {
    var mediaUrl by rememberSaveable { mutableStateOf("") }

    Scaffold(bottomBar = { BottomNav(navController = navController) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {

            val (heading, urlTextField, uploadButton) = createRefs()
            Heading(modifier = Modifier.constrainAs(heading) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            TextField(
                value = mediaUrl,
                singleLine = true,
                placeholder = {

                    Text(
                        fontSize = 12.sp,
                        text = "Paste a YouTube / SoundCloud / Deezer link"
                    )
                },
                onValueChange = { url ->
                    mediaUrl = url
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background,
                    textColor = MaterialTheme.colors.onBackground,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = stringResource(
                            R.string.add_icon
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .constrainAs(urlTextField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(heading.bottom)
                        centerVerticallyTo(parent)
                    }
            )

            DefaultButton(R.string.upload,
                Modifier
                    .padding(top = 24.dp)
                    .constrainAs(uploadButton) {
                        top.linkTo(urlTextField.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                if (mediaUrl.isNotEmpty()) {
                    uploadViewModel.dispatch(UploadAction.GetUploadedTrackResults(mediaUrl))
                    navController.navigate("new_track")
                    mediaUrl = ""
                }
            }
        }
    }
}


@Composable
fun Heading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = "OpenAudio",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.alpha(0.3f),
            textAlign = TextAlign.Center,
        )
        Text(
            text = "MUSIC BY MUSIC LOVERS",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.alpha(0.3f),
            textAlign = TextAlign.Center
        )
    }

}