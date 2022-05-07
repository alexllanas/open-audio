package com.alexllanas.openaudio.presentation.main.state

import com.alexllanas.core.domain.models.Track
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

data class MediaPlayerState(
    val currentPlayingTrack: Track? = null,
    val duration: Float = 0F,
    val currentSecond: Float = 0F,
    val videoId: String = "",
    val isPlaying: Boolean = false,
    val tracker: YouTubePlayerTracker? = null,
    val youTubePlayer: YouTubePlayer? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
