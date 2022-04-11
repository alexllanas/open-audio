package com.alexllanas.openaudio.presentation.models

import android.os.Parcelable
import com.alexllanas.core.domain.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaylistUIModel(
    val name: String? = null,
    var coverImage: String? = null,
    val url: String,
    var author: UserUIModel? = null
) : Parcelable