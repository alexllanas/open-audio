package com.alexllanas.openaudio.presentation.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.DefaultButton
import com.alexllanas.openaudio.presentation.compose.theme.FacebookBlue
import com.alexllanas.openaudio.presentation.util.ColorUtil

@Composable
fun LandingScreen(onEmailClick: () -> Unit, onLoginClick: () -> Unit) {
    val context = LocalContext.current

    val modifier = Modifier
        .fillMaxSize()
        .background(colors.background)
        .padding(16.dp)
    ConstraintLayout(
        modifier = modifier
    ) {
        val (heading, subHeading, signUpEmailButton, loginButton) = createRefs()
        Text(
            text = "OpenAudio",
            color = colors.onBackground,
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
            color = colors.onBackground,
            modifier = Modifier
                .constrainAs(subHeading) {
                    top.linkTo(heading.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        DefaultButton(
            stringResource = R.string.sign_up,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(signUpEmailButton) {
                    bottom.linkTo(loginButton.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            launchWebIntent(context)
//            onEmailClick()
        }
        DefaultButton(
            stringResource = R.string.login,
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
            onLoginClick()
        }
    }
}

private fun launchWebIntent(context: Context) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(Constants.REGISTER_URL)
    context.startActivity(i)
}