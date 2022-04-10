package com.alexllanas.openaudio.presentation.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.auth.state.AuthAction
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.dispatch(
            AuthAction.Login.LoginAction(
                "testOpenAudio@gmail.com",
                "ducksquad1!"
            )
        )
        homeViewModel.dispatch(HomeAction.LoadStream("whydSid=s%3AbS7XKNXe0m5ZaNR7HIwCmtDSL_HDYIBx.KIlQu%2F0PrwEfbReaCMebbWKeE2LKRKznRTAzSxGidto"))
        setContent {
            MainScreen(homeViewModel = homeViewModel, mainViewModel = mainViewModel)
        }
    }
}