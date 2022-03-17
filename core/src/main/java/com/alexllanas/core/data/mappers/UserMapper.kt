package com.alexllanas.core.data.mappers

import com.alexllanas.common.responses.UserResponse
import com.alexllanas.core.domain.Track
import com.alexllanas.core.domain.User

fun UserResponse.toDomainUser() = User(
    id = id,
    name = name,
    email = email,
    playlists = playlists.map { pl -> pl.toDomainPlaylist() },
    tracks = tracks.map { t -> t.toDomainTrack() },
    subscriptions = subscriptions.toDomainUserList(),
    subscribers = subscribers.toDomainUserList(),
    subscriptionCount = subscriptionCount,
    subscriberCount = subscriberCount,
    lastTrack = lastTrack?.toDomainTrack(),
    location = location,
    bio = bio
)

fun List<UserResponse>.toDomainUserList(): List<User> {
    return mapNotNull { userResponse -> userResponse.toDomainUser() }
}