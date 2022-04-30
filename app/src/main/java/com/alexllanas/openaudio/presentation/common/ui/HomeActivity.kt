package com.alexllanas.openaudio.presentation.common.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.auth.state.AuthViewModel
import com.alexllanas.openaudio.presentation.dataStore
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.MainScreen
import com.alexllanas.openaudio.presentation.profile.state.ProfileViewModel
import com.alexllanas.openaudio.presentation.upload.state.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val uploadViewModel: UploadViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

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
            MainScreen(
                homeViewModel = homeViewModel,
                mainViewModel = mainViewModel,
                authViewModel = authViewModel,
                uploadViewModel = uploadViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}
