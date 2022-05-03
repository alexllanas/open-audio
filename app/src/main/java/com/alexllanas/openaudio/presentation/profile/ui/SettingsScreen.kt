package com.alexllanas.openaudio.presentation.profile.ui

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.NavHostController
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.ui.AuthFragment
import com.alexllanas.openaudio.presentation.compose.components.BasicPasswordField
import com.alexllanas.openaudio.presentation.compose.components.SaveTopBar
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.main.ui.MainFragment
import com.alexllanas.openaudio.presentation.main.ui.NavItem
import com.alexllanas.openaudio.presentation.profile.state.ProfileAction
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel

@Composable
fun SettingsScreen(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    mainState: MainState,
    navHostController: NavHostController,
    fragmentManager: FragmentManager?
) {
    Scaffold(
        topBar = {
            SaveTopBar("Settings", { navHostController.popBackStack() }, false)
        },
        bottomBar = { BottomNav(navController = navHostController) }

    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            val (passwordFields, logoutButton) = createRefs()

            // password fields
            PasswordFields(
                profileViewModel = profileViewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(passwordFields) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            // logout button
            Button(onClick = {
                mainState.sessionToken?.let { token ->
                    fragmentManager?.commit {
                        setReorderingAllowed(true)
                        replace(R.id.nav_host_fragment, AuthFragment())
                    }
                    profileViewModel.dispatch(ProfileAction.Logout(token))
//                    mainViewModel.dispatch(AuthAction.ClearSessionTokenAction(""))
                    mainViewModel.dispatch(AuthAction.ClearMainState)
                    homeViewModel.dispatch(HomeAction.ClearHomeState)

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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Change Password",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        BasicPasswordField(
            value = profileState.currentPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.CurrentPasswordTextChanged(password))
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(R.string.current_password),
                )
            })
        BasicPasswordField(
            value = profileState.newPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.NewPasswordTextChanged(password))
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.new_password)) },
        )
        BasicPasswordField(
            value = profileState.confirmPasswordText,
            onValueChange = { password ->
                profileViewModel.dispatch(ProfileAction.ConfirmPasswordTextChanged(password))
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.confirm_password)) })
    }
}