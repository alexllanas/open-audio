package com.alexllanas.openaudio.presentation.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alexllanas.openaudio.presentation.auth.ForgotPasswordScreen
import com.alexllanas.openaudio.presentation.main.state.MainViewModel

sealed class AuthNavItem(
    var title: String,
    var icon: ImageVector? = null,
    var screenRoute: String
) {
    object Landing : AuthNavItem("Landing", null, "landing")
    object ForgotPassword : AuthNavItem("ForgotPassword", null, "forgot_password")
    object Login : AuthNavItem("Login", null, "login")
    object Register : AuthNavItem("Register", null, "register")
}

@Composable
fun AuthNavigationGraph(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthNavItem.Landing.screenRoute
    ) {
        composable(AuthNavItem.ForgotPassword.screenRoute) {
            ForgotPasswordScreen(navHostController)
        }
        composable(AuthNavItem.Login.screenRoute) {
            LoginScreen(mainViewModel, navController = navHostController)
        }
        composable(AuthNavItem.Register.screenRoute) {
            RegisterScreen(navHostController)
        }
        composable(AuthNavItem.Landing.screenRoute) {
            LandingScreen(
                {
                    navHostController.navigate(AuthNavItem.Register.screenRoute)
                },
                {
                    navHostController.navigate(AuthNavItem.Login.screenRoute)
                }
            )
        }
    }
}