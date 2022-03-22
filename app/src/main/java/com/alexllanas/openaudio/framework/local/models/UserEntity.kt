package com.alexllanas.openaudio.framework.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,
    val playlists: List<PlaylistEntity> = emptyList(),
    val tracks: List<TrackEntity> = emptyList(),
    val subscriptions: List<UserEntity> = emptyList(),
    val subscribers: List<UserEntity> = emptyList(),
    @ColumnInfo(name = "subscription_count")
    val subscriptionCount: Int = 0,
    @ColumnInfo(name = "subscriber_count")
    val subscriberCount: Int = 0,
    val lastTrack: TrackEntity? = null,
    val location: String? = null,
    val bio: String? = null,
    @ColumnInfo(name = "is_subscribing")
    val isSubscribing: Boolean = false
)