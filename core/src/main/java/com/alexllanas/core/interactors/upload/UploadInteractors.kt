package com.alexllanas.core.interactors.upload

import com.alexllanas.core.interactors.common.AddTrack
import com.alexllanas.core.interactors.common.AddTrackToPlaylist
import com.alexllanas.core.interactors.common.LikeTrack
import com.alexllanas.core.interactors.common.UnlikeTrack

data class UploadInteractors(
    val addTrack: AddTrack,
    val addTrackToPlaylist: AddTrackToPlaylist,
    val likeTrack: LikeTrack,
    val unlikeTrack: UnlikeTrack,
)