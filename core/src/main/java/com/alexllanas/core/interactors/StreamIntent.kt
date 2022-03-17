package com.alexllanas.core.interactors

sealed class StreamIntent {
    object Initial : StreamIntent() // when user first navigates to this screen
}
