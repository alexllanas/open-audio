package com.alexllanas.core.interactors.upload

import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.core.interactors.common.AddTrack
import com.alexllanas.core.interactors.common.AddTrackToPlaylist
import com.alexllanas.core.interactors.common.LikeTrack
import com.alexllanas.core.interactors.common.UnlikeTrack
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class UploadInteractors(
    val addTrack: AddTrack,
    val addTrackToPlaylist: AddTrackToPlaylist,
    val getTrackMetadata: GetTrackMetadata,
    val likeTrack: LikeTrack,
    val unlikeTrack: UnlikeTrack,
)