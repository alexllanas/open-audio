package com.alexllanas.openaudio.framework.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String? = null,
    val title: String? = null,
    @ColumnInfo(name = "media_url")
    val mediaUrl: String? = null,
    val image: String? = null,
    @ColumnInfo(name = "user_like_ids")
    val userLikeIds: List<String> = emptyList(),
    val liked: Boolean = false
)