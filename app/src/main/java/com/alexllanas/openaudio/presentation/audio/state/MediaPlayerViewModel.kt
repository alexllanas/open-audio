package com.alexllanas.openaudio.presentation.audio.state


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.interactors.home.HomeInteractors
import com.alexllanas.openaudio.presentation.audio.MediaPlayerAction
import com.alexllanas.openaudio.presentation.audio.SetPlayQueueChange
import com.alexllanas.openaudio.presentation.common.state.Action
import com.alexllanas.openaudio.presentation.home.state.*
import com.alexllanas.openaudio.presentation.main.state.PartialStateChange
import com.alexllanas.openaudio.presentation.mappers.toDomain
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    private val homeInteractors: HomeInteractors,
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
//        Log.d(TAG, "dispatch: $action")
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    private fun SharedFlow<Action>.toChangeFlow(): Flow<PartialStateChange<MediaPlayerState>> {
        val executeSetPlayQueue: suspend (List<Track>) -> Flow<PartialStateChange<MediaPlayerState>> =
            { change ->
                flow {
                    emit(SetPlayQueueChange.Data(change))
                }
            }
        val executeClearMediaPlayerState: suspend () -> Flow<PartialStateChange<MediaPlayerState>> =
            {
                flow {
                    emit(ClearMediaPlayerStateChange())
                }
            }
        val executeSetCurrentSecond: suspend (Float) -> Flow<PartialStateChange<MediaPlayerState>> =
            { currentSecond ->
                flow {
                    emit(SetCurrentSecondChange.Data(currentSecond))
                }
            }
        val executeSetPlaybackState: suspend (PlayerConstants.PlayerState) -> Flow<PartialStateChange<MediaPlayerState>> =
            { playbackState ->
                flow {
                    emit(SetPlaybackState.Data(playbackState))
                }
            }
        val executeSetDuration: suspend (Float) -> Flow<PartialStateChange<MediaPlayerState>> =
            { duration ->
                flow {
                    emit(SetDurationChange.Data(duration))
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
        val executeToggleCurrentTrackLike: suspend (String, String) -> Flow<PartialStateChange<MediaPlayerState>> =
            { trackId, sessionToken ->
                homeInteractors.toggleLike(trackId, sessionToken)
                    .map { result ->
                        result.fold(
                            ifLeft = { ToggleCurrentTrackLikeChange.Error(it) },
                            ifRight = {
                                ToggleCurrentTrackLikeChange.Data(it, trackId)
                            }
                        )
                    }.onStart { ToggleLikeStreamTrackChange.Loading }
            }
        return merge(
            filterIsInstance<MediaPlayerAction.SetPlayQueue>()
                .flatMapConcat { executeSetPlayQueue(it.queue) },
            filterIsInstance<HomeAction.SetPlaybackState>()
                .flatMapConcat { executeSetPlaybackState(it.playbackState) },
            filterIsInstance<HomeAction.SetCurrentSecond>()
                .flatMapConcat { executeSetCurrentSecond(it.currentSecond) },
            filterIsInstance<HomeAction.SetDuration>()
                .flatMapConcat { executeSetDuration(it.duration) },
            filterIsInstance<HomeAction.ToggleCurrentTrackLike>()
                .flatMapConcat { executeToggleCurrentTrackLike(it.trackId, it.sessionToken) },
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

    fun isPlaying() = mediaPlayerState.value.playbackState == PlayerConstants.PlayerState.PLAYING
    fun isBuffering() =
        mediaPlayerState.value.playbackState == PlayerConstants.PlayerState.BUFFERING

    fun isEnded() = mediaPlayerState.value.playbackState == PlayerConstants.PlayerState.ENDED

    fun playRandomTrack() {
        var track = mediaPlayerState.value.playQueue[Random.nextInt(
            0,
            mediaPlayerState.value.playQueue.size - 1
        )]
        while (track.mediaUrl?.startsWith("/yt/") == false) {
            track = mediaPlayerState.value.playQueue[Random.nextInt(
                0,
                20
            )]
        }
        dispatch(
            HomeAction.SetCurrentTrack(
                track
            )
        )
        track.mediaUrl?.let { videoId ->
            dispatch(HomeAction.SetVideoId(videoId))
        }
    }
}