package com.alexllanas.core.interactors.common

import arrow.core.Either
import com.alexllanas.core.data.remote.playlist.PlaylistDataSource
import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.User
import kotlinx.coroutines.flow.Flow

class CreatePlaylist(
    private val playlistDataSource: PlaylistDataSource
) {
    suspend operator fun invoke(
        playlistName: String,
        user: User,
        sessionToken: String
    ): Flow<Either<Throwable, Playlist>> {
        return if (playlistName.isNotBlank()) {
            playlistDataSource.createPlaylist(playlistName, sessionToken)
        } else {
            playlistDataSource.createPlaylist(
                "New Playlist #${user.playlists.size + 1}",
                sessionToken
            )
        }

    }
}
