package com.alexllanas.openaudio.framework.mappers

import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.framework.network.models.UserResponse

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