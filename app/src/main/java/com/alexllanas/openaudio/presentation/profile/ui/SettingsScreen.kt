package com.alexllanas.openaudio.presentation.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.SaveTopBar
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import com.alexllanas.openaudio.presentation.profile.state.ProfileState
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel

@Composable
fun SettingsScreen(
    profileViewModel: ProfileViewModel,
    mainState: MainState,
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Settings",
//                        style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
//                    )
//                },
//                navigationIcon = {
//                    Text("Save", modifier = Modifier.padding(8.dp))
//                    //                    Icon(
//                    //                        imageVector = Icons.Default.ArrowBack,
//                    //                        contentDescription = stringResource(R.string.back_arrow)
//                    //                    )
//                }
//            )
            SaveTopBar("Settings", {}, false)
        },
        bottomBar = { BottomNav(navController = navHostController) }

    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val (passwordFields, logoutButton) = createRefs()

            // password fields
            PasswordFields(
                profileViewModel = profileViewModel,
                modifier = Modifier
                    .constrainAs(passwordFields) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            // logout button
            Button(onClick = {
                mainState.sessionToken?.let { token ->
                    profileViewModel.dispatch(ProfileAction.Logout(token))
                }
            }, modifier = Modifier
                .padding(top = 64.dp)
                .constrainAs(logoutButton) {
                    top.linkTo(passwordFields.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun PasswordFields(profileViewModel: ProfileViewModel, modifier: Modifier = Modifier) {
    val profileState by profileViewModel.profileState.collectAsState()
    Column(modifier = modifier) {
        Text(
            "Change Password",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 16.dp)
        )

        PasswordField(
            modifier = Modifier.padding(top = 8.dp),
            password = profileState.currentPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.CurrentPasswordTextChanged(password))
            },
            label = {
                Text(
                    text = stringResource(R.string.current_password),
                )
            })
        PasswordField(
            password = profileState.newPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.NewPasswordTextChanged(password))
            },
            label = { Text(text = stringResource(R.string.new_password)) },
        )
        PasswordField(password = profileState.confirmPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.ConfirmPasswordTextChanged(password))
            },
            label = { Text(text = stringResource(R.string.confirm_password)) })
    }
}

@Composable
fun PasswordField(
    password: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit),
    modifier: Modifier = Modifier
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
        placeholder = label,
        label = label,
        singleLine = true,
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
}
