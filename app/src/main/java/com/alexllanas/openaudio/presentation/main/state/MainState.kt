package com.alexllanas.openaudio.presentation.main.state

import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

data class MainState(
    val loggedInUser: User? = null,
    val sessionToken: String? = null,
    val currentPlayingTrack: Track? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val youTubePlayer: YouTubePlayer? = null
)