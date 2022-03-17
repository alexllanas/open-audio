package com.alexllanas.openaudio.presentation.home

import com.alexllanas.common.Intent

sealed class StreamIntent : Intent {
    data class Initial(val sessionToken: String) :
        StreamIntent() // when user first navigates to this screen
}
