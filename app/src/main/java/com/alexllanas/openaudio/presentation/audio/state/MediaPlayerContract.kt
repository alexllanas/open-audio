package com.alexllanas.openaudio.presentation.audio

import com.alexllanas.core.domain.models.Track
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerState
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange

sealed class MediaPlayerAction : Action() {
    data class SetPlayQueue(val queue: List<Track>) : Action()
}

sealed class SetPlayQueueChange : PartialStateChange<MediaPlayerState> {
    override fun reduce(state: MediaPlayerState): MediaPlayerState {
        return when (this) {
            is Data -> state.copy(playQueue = queue)
        }
    }

    data class Data(val queue: List<Track>) : SetPlayQueueChange()
}
