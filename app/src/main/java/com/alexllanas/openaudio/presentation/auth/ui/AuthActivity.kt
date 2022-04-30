package com.alexllanas.openaudio.presentation.auth.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.compose.rememberNavController
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.dataStore
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenFlow = dataStore.data.map {
            it[SESSION_TOKEN]
        }.asLiveData()
        tokenFlow.observe(this) {
            it?.let { token ->
                mainViewModel.dispatch(AuthAction.SetSessionTokenAction(token))
            }
        }
        setContent {
            val navController = rememberNavController()
            AuthNavigationGraph(
                navController,
                mainViewModel
            )
        }
    }
}
