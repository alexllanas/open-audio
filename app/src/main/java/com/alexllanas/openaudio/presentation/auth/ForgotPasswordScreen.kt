package com.alexllanas.openaudio.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BasicEmailField
import com.alexllanas.openaudio.presentation.compose.components.appbars.TitleBackBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.alexllanas.openaudio.presentation.util.isValidEmail


@Composable
fun ForgotPasswordScreen(navHostController: NavHostController) {
    var isError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TitleBackBar { navHostController.popBackStack() }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .size(100.dp),
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.forgot_pw),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = stringResource(R.string.confirm_email_reset_pw),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
            BasicEmailField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                label = { Text(text = "email") },
                isError = isError
            )
            Button(
                onClick = {
                    isError = !email.isValidEmail()
                },
                modifier = Modifier
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = "Reset Password"
                )
            }
        }
    }

}
