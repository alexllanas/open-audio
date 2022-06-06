package com.alexllanas.core.util

class Constants {

    companion object {
        /**
         * Notifications
         */
        const val CHANNEL_ID = "open_audio_media_control_channel"


        const val FOLLOWERS: String = "Followers"
        const val FOLLOWING: String = "Following"

        /**
         * Logging
         */
        const val TAG: String = "openAudioDebug"

        /**
         * Network
         */
        const val BASE_URL = "https://openwhyd.org"
        const val REGISTER_URL = "https://openwhyd.org/#signup"
        const val PAGE_LIMIT = 50
        const val GET_TRACK_METADATA_ERROR = "Error fetching track metadata."
        const val ERROR = "An error has occurred."

        const val VIDEO_ID_REGEX = """(?<=youtu.be/|watch\?v=|/videos/|embed\\/)[^#\&\?]*"""

        /**
         * Database
         */
        const val DATABASE_NAME = "open_audio_db"


        /**
         * Testing
         */
        const val SAMPLE_MEDIA_URL = "/yt/3SeOVVJXOUo"
        const val ERROR_FLAG = "error_flag"
        const val SAMPLE_TRACK_TITLE =
            "Lofi Hip Hop Chillhop Music Mix  GEMN Chill Lo fi Hip Hop Beats FREE 2021"
        const val SAMPLE_TRACK_THUMBNAIL_URL = "https://i.ytimg.com/vi/3SeOVVJXOUo/hqdefault.jpg"
        const val NEW_TRACK_ID = "new_track_id"
    }
}
