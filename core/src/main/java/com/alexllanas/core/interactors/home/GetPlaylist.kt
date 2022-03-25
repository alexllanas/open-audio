package com.alexllanas.core.interactors.home

import com.alexllanas.core.data.remote.playlist.PlaylistDataSource

class GetPlaylist(private val playlistDataSource: PlaylistDataSource) {
    suspend operator fun invoke(playlistId: String) = playlistDataSource.getPlaylist(playlistId)
}