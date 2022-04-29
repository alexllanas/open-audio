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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.NavItem

@Composable
fun LoginScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val mainState by mainViewModel.mainState.collectAsState()
    Log.d(TAG, "LoginScreen: error = ${mainState.error?.printStackTrace()}")

    Log.d(TAG, "LoginScreen: isLoading = ${mainState.isLoading}")
    ConstraintLayout(
        Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (heading, emailTextField, passwordTextField, forgotText, loginButton, facebookButton) = createRefs()

        Text("Welcome Back!", style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(heading) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        // Edit Text, email
        TextField(value = email,
            onValueChange = {
                email = it
            },
            singleLine = true,
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
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
            label = { Text("password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .constrainAs(passwordTextField) {
                    top.linkTo(emailTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.VisibilityOff
                else
                    Icons.Filled.Visibility
                val description =
                    if (passwordVisible)
                        stringResource(R.string.show_password)
                    else
                        stringResource(R.string.hide_password)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
        Button(onClick = {
            if (validateLogin(email.trim(), email.trim())) {
                mainViewModel.dispatch(
                    AuthAction.Login.LoginAction(
                        email.trim(),
                        password.trim()
                    )
                )
                navController.navigate(NavItem.Stream.screenRoute)
            }
        },
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(24.dp)
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
                .padding(24.dp)
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

