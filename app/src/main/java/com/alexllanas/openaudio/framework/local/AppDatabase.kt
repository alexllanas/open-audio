package com.alexllanas.openaudio.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexllanas.openaudio.framework.local.models.PlaylistEntity
import com.alexllanas.openaudio.framework.local.models.TrackEntity
import com.alexllanas.openaudio.framework.local.models.UserEntity

@Database(entities = [TrackEntity::class, PlaylistEntity::class, UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun userDao(): UserDao

}