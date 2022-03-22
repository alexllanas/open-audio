package com.alexllanas.openaudio.framework.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String? = null,
    val name: String? = null,
    @ColumnInfo(name = "track_count")
    val trackCount: Int = 0,
    @ColumnInfo(name = "cover_image")
    val coverImage: String? = null,
    val url: String? = null,
    var author: UserEntity? = null
)