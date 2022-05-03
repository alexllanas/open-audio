package com.alexllanas.openaudio.presentation.main.state

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

data class MediaPlayerState(
    val videoId: String = "",
    val isPlaying: Boolean = false,
    val tracker: YouTubePlayerTracker? = null
)
