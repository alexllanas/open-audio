package com.alexllanas.openaudio.presentation.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.presentation.compose.theme.FacebookBlue
import com.alexllanas.openaudio.presentation.util.ColorUtil

@Composable
fun LandingScreen2() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(200.dp))
            Text("OpenAudio", style = MaterialTheme.typography.h2)
            Text("MUSIC BY MUSIC LOVERS", style = MaterialTheme.typography.subtitle1)
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            Button(onClick = {}) {
                Text("SIGN UP WITH FACEBOOK")
            }
            Button(onClick = {}) {
                Text("SIGN UP WITH EMAIL")
            }
            Button(onClick = {}) {
                Text("LOGIN")
            }
        }
    }
}

@Composable
fun LandingScreen(onEmailClick: () -> Unit, onLoginClick: () -> Unit) {
    val modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
    ConstraintLayout(
        modifier = modifier
    ) {
        val (heading, subHeading, signUpFacebookButton, signUpEmailButton, loginButton) = createRefs()
        Text(
            text = "OpenAudio",
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .padding(top = 200.dp)
                .constrainAs(heading) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(subHeading.top)
                }
        )
        Text(text = "MUSIC BY MUSIC LOVERS",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .constrainAs(subHeading) {
                    top.linkTo(heading.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
//        Button(onClick = {},
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = FacebookBlue
//            ),
//            modifier = Modifier
//                .fillMaxWidth()w
//                .padding(bottom = 8.dp)
//                .height(48.dp)
//                .constrainAs(signUpFacebookButton) {
//                    bottom.linkTo(signUpEmailButton.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }) {
//            Text("SIGN UP WITH FACEBOOK", color = Color.White)
//        }
        Button(onClick = { onEmailClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(signUpEmailButton) {
                    bottom.linkTo(loginButton.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            Text("SIGN UP WITH EMAIL")
        }
        Button(
            onClick = { onLoginClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray
            ),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 64.dp)
                .height(48.dp)
                .fillMaxWidth()
                .constrainAs(loginButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("LOGIN")
        }

    }
}

@Preview
@Composable
fun previewLandingScreen() {
    LandingScreen({}) {}
}
