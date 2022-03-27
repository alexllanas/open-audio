package com.alexllanas.openaudio.presentation.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val actions = Channel<AuthAction>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        authViewModel.dispatch(AuthAction.Login.EmailTextChangedAction("testOpenAudio@gmail.com"))
        authViewModel.dispatch(AuthAction.Login.PasswordTextChangedAction("ducksquad1!"))
        authViewModel.dispatch(AuthAction.Register.NameTextChangedAction("Register.NameTextChangedAction"))
        authViewModel.dispatch(AuthAction.Register.EmailTextChangedAction("Register.EmailTextChangedAction"))
        authViewModel.dispatch(AuthAction.Register.PasswordTextChangedAction("Register.PasswordTextChangedAction"))

        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen()
            }
        }
    }

    @Suppress("FunctionName")
    @Composable
    fun MainScreen() {
        val authState by authViewModel.authState.collectAsState()
        val mainState by mainViewModel.mainState.collectAsState()
        mainViewModel.dispatch(
            AuthAction.Login.LoginAction(
                authState.loginEmailText,
                authState.loginPasswordText
            )
        )
        Column {
            SomeText(authState.loginEmailText)
            SomeText(authState.loginPasswordText)
            SomeText(authState.registerNameText)
            SomeText(authState.registerEmailText)
            SomeText(authState.registerPasswordText)
            SomeText(mainState.loggedInUser?.name ?: "no user")
        }
    }

    @Suppress("FunctionName")
    @Composable
    private fun SomeText(text: String) {
        Column {
            Text(
                text = " $text",
                color = Color.Cyan,
                fontSize = 15.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
