package com.alexllanas.core.interactors.home

import com.alexllanas.core.interactors.common.*

data class HomeInteractors(
    val getFollowers: GetFollowers,
    val getFollowing: GetFollowing,
    val getPlaylist: GetPlaylist,
    val getPlaylistTracks: GetPlaylistTracks,
    val getStream: GetStream,
    val getUser: GetUser,
    val getUserTracks: GetUserTracks,
    val likeTrack: LikeTrack,
    val unlikeTrack: UnlikeTrack,
    val search: Search,
    val followUser: FollowUser,
    val unfollowUser: UnfollowUser,
    val addTrackToPlaylist: AddTrackToPlaylist,
    val toggleLike: ToggleLike,
    val createPlaylist: CreatePlaylist
)