package com.alexllanas.openaudio.presentation.main.state


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.home.state.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
) : ViewModel() {
    private val initialMainState: MediaPlayerState by lazy { MediaPlayerState() }
    var mediaPlayerState: StateFlow<MediaPlayerState>

    private val _actions = MutableSharedFlow<Action>()
    private val actions = _actions.asSharedFlow()

    init {
        mediaPlayerState =
            actions
                .toChangeFlow()
                .scan(initialMainState) { state, change -> change.reduce(state) }
                .stateIn(
                    viewModelScope,
                    SharingStarted.Eagerly,
                    initialMainState
                )

    }

    fun dispatch(action: Action) {
        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private fun SharedFlow<Action>.toChangeFlow(): Flow<PartialStateChange<MediaPlayerState>> {
        val executeClearMediaPlayerState: suspend () -> Flow<PartialStateChange<MediaPlayerState>> =
            {
                flow {
                    emit(ClearMediaPlayerStateChange())
                }
            }
        val executeSetCurrentTrack: suspend (Track) -> Flow<PartialStateChange<MediaPlayerState>> =
            { track ->
                flow {
                    emit(SetCurrentTrackChange.Data(track))
                }
            }
        val executeSetTracker: suspend (YouTubePlayerTracker) -> Flow<PartialStateChange<MediaPlayerState>> =
            { tracker ->
                flow {
                    emit(SetTrackerChange.Data(tracker))
                }
            }
        val executeSetYoutubePlayer: suspend (YouTubePlayer) -> Flow<PartialStateChange<MediaPlayerState>> =
            { youtubePlayer ->
                flow {
                    emit(SetYouTubePlayerChange.Data(youtubePlayer))
                }
            }
        val executeSetIsPlaying: suspend (Boolean) -> Flow<PartialStateChange<MediaPlayerState>> =
            { isPlaying ->
                flow {
                    emit(SetIsPlayingChange.Data(isPlaying))
                }
            }
        val executeSetVideoId: suspend (String) -> Flow<PartialStateChange<MediaPlayerState>> =
            { videoId ->
                flow {
                    emit(SetVideoIdChange.Data(videoId))
                }
            }
        return merge(
            filterIsInstance<HomeAction.SetCurrentTrack>()
                .flatMapConcat { executeSetCurrentTrack(it.track) },
            filterIsInstance<HomeAction.ClearMediaPlayerState>()
                .flatMapConcat { executeClearMediaPlayerState() },
            filterIsInstance<HomeAction.SetYoutubePlayer>()
                .flatMapConcat { executeSetYoutubePlayer(it.youTubePlayer) },
            filterIsInstance<HomeAction.SetMediaTracker>()
                .flatMapConcat { executeSetTracker(it.tracker) },
            filterIsInstance<HomeAction.SetIsPlaying>()
                .flatMapConcat { executeSetIsPlaying(it.isPlaying) },
            filterIsInstance<HomeAction.SetVideoId>()
                .flatMapConcat { executeSetVideoId(it.videoId) },
        )
    }
}