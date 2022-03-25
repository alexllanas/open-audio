package com.alexllanas.core.interactors.home

import com.alexllanas.core.data.remote.playlist.PlaylistDataSource

class GetPlaylistTracks(private val playlistDataSource: PlaylistDataSource) {
    suspend operator fun invoke(playlistUrl: String) = playlistDataSource.getPlaylistTracks(playlistUrl)

}