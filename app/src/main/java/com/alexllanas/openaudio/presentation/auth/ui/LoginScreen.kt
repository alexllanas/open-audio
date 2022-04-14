package com.alexllanas.openaudio.presentation.auth.ui

import com.alexllanas.openaudio.R.drawable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    val state by authViewModel.authState.collectAsState()

    ConstraintLayout(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (heading, emailTextField, passwordTextField, forgotText, facebookButton) = createRefs()
        Text("Welcome Back!", style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(heading) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        // Edit Text, email
        TextField(value = state.loginEmailText,
            onValueChange = { email ->
                authViewModel.dispatch(AuthAction.Login.EmailTextChangedAction(email))
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text("email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .constrainAs(emailTextField) {
                    top.linkTo(heading.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        // Edit Text, password
        TextField(value = state.loginPasswordText,
            onValueChange = { password ->
                authViewModel.dispatch(AuthAction.Login.PasswordTextChangedAction(password))
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text("password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        // Text, blue
        Text(text = "Forgot password",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    // TODO: dialog
                }
                .constrainAs(forgotText) {
                    top.linkTo(passwordTextField.bottom)
                    start.linkTo(parent.start)
                }
        )
        Image(
            painter = painterResource(drawable.facebook_icon),
            contentDescription = stringResource(R.string.facebook_login_button),
            modifier = Modifier.constrainAs(facebookButton) {
                top.linkTo(passwordTextField.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }


}

@Preview
@Composable
fun LoginScreenPreview() {

}

