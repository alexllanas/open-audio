package com.alexllanas.openaudio.presentation.auth.ui

import android.util.Log
import com.alexllanas.openaudio.R.drawable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.compose.components.BasicEmailField
import com.alexllanas.openaudio.presentation.compose.components.BasicPasswordField
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.NavItem
import com.alexllanas.openaudio.presentation.util.isValidEmail

@Composable
fun LoginScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val mainState by mainViewModel.mainState.collectAsState()

    mainState.sessionToken?.let {
        mainState.loggedInUser?.let {
            LaunchedEffect(it) {
                navController.navigate(NavItem.Stream.screenRoute)
            }
        }
    }

    ConstraintLayout(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (heading, emailTextField, passwordTextField, forgotText, loginButton, facebookButton) = createRefs()

        Text(
            "Welcome Back!", style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth()
                .constrainAs(heading) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    centerHorizontallyTo(parent)
                },
            textAlign = TextAlign.Center
        )

        BasicEmailField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .constrainAs(emailTextField) {
                    top.linkTo(heading.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = { Text("email") },
            isError = isEmailError
        )
        BasicPasswordField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = { Text("password") },

            )
        Text(text = "Forgot password",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(top = 32.dp)
                .clickable {
                    navController.navigate(NavItem.ForgotPassword.screenRoute)
                }
                .constrainAs(forgotText) {
                    top.linkTo(passwordTextField.bottom)
                    start.linkTo(parent.start)
                }
        )
        Button(onClick = {
            isEmailError = !email.isValidEmail()

            if (!isEmailError) {
                mainViewModel.dispatch(
                    AuthAction.Login.LoginAction(
                        email.trim(),
                        password.trim()
                    )
                )
//                navController.navigate(NavItem.Stream.screenRoute)
            }
        },
            modifier = Modifier
                .fillMaxWidth(.75f)
                .padding(top = 32.dp)
                .constrainAs(loginButton) {
                    top.linkTo(forgotText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            Text(stringResource(R.string.login))
        }

        Image(
            painter = painterResource(drawable.facebook_icon),
            contentDescription = stringResource(R.string.facebook_login_button),
            modifier = Modifier
                .padding(top = 24.dp)
                .size(54.dp)
                .constrainAs(facebookButton) {
                    top.linkTo(loginButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

fun validateLogin(email: String, password: String): Boolean {
    if (email.isBlank() || password.isBlank()) return false

    return true
}

