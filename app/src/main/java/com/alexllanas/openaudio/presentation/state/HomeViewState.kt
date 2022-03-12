package com.alexllanas.openaudio.presentation.state

import com.alexllanas.common.state.ViewState
import com.alexllanas.common.utils.NetworkError
import com.alexllanas.common.utils.Resource
import com.alexllanas.core.domain.Playlist
import com.alexllanas.core.domain.User
import com.alexllanas.core.domain.Track

class HomeViewState(
    var query: String = "",
    var streamTracks: List<Track> = emptyList(),
    var resultTracks: List<Track> = emptyList(),
    var resultUsers: List<User> = emptyList(),
    var resultPlaylists: List<Playlist> = emptyList(),
    var selectedTrack: Track? = null,
    var selectedUser: User? = null,
    var selectedPlaylist: Playlist? = null,
    var likeUsers: List<User> = emptyList(),
    var error: NetworkError? = null
) : ViewState()