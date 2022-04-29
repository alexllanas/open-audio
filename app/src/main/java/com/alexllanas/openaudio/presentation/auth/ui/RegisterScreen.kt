package com.alexllanas.openaudio.presentation.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BasicEmailField
import com.alexllanas.openaudio.presentation.compose.components.BasicInputField
import com.alexllanas.openaudio.presentation.compose.components.BasicPasswordField
import com.alexllanas.openaudio.presentation.compose.components.appbars.TitleBackBar
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import com.alexllanas.openaudio.presentation.util.isValidEmail

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(navHostController: NavHostController) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TitleBackBar(stringResource(R.string.sign_up)) {
                navHostController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            BasicInputField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                label = { Text("name") },
            )
            BasicEmailField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                label = { Text("email") },
                isError = isEmailError
            )
            BasicPasswordField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                label = { Text("password") },
            )
            Button(
                onClick = {
                    isEmailError = !email.isValidEmail()
                },
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth(.75f)
            ) {
                Text(
                    text = stringResource(R.string.sign_up)
                )
            }
        }
    }
}