package com.alexllanas.testing

import com.alexllanas.core.domain.models.Playlist
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.Constants

internal const val NETWORK_ERROR_MESSAGE = "Something went wrong with the network.."

internal val TRACKS = listOf(
    Track(id = "1", title = "3rd Planet - Modest Mouse"),
    Track(id = "2", title = "In Your Eyes - BADBADNOTGOOD"),
    Track(id = "3", title = "The Lovecats - The Cure")
)

internal val PLAYLISTS = listOf(
    Playlist(id = "4", name = "easy listening", trackCount = 4),
    Playlist(id = "5", name = "favorites", trackCount = 13),
    Playlist(id = "6", name = "indie", trackCount = 9)
)

internal val USERS = listOf(
    User(id = "11", name = "Alex", "fake@email.com"),
    User(id = "23", name = "Lily", "lily4@gmail.com"),
    User(id = "54", name = "Walt", "fake.email@yahoo.com")
)

internal val POSTS = listOf(
    Track(id = "4", title = "Fearless - Pink Floyd"),
    Track(id = "5", title = "Blondes Have More Fun - Forth Wanderers"),
    Track(id = "6", title = "Idiot Waltz - The Gun Club")
)

internal val SEARCH_MAP_RESULTS = hashMapOf(
    "tracks" to TRACKS,
    "playlists" to PLAYLISTS,
    "users" to USERS,
    "posts" to POSTS
)

internal val NEW_TRACK = Track(
    id = Constants.NEW_TRACK_ID,
    title = Constants.SAMPLE_TRACK_TITLE,
    mediaUrl = Constants.SAMPLE_MEDIA_URL,
    image = Constants.SAMPLE_TRACK_THUMBNAIL_URL
)



internal val NO_EXISTING_TRACKS_UPLOAD_RESULTS = arrayListOf(NEW_TRACK)