package com.alexllanas.openaudio.presentation.auth.state

import androidx.lifecycle.ViewModel
import com.alexllanas.core.interactors.auth.AuthInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel() {


}