package com.alexllanas.openaudio.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TrackUIModel(
    val id: String? = null,
    val title: String? = null,
    val image: String? = null,
    val mediaUrl: String? = null,
    val userLikeIds: List<String> = emptyList(),
    var liked: Boolean = false
) : Parcelable
