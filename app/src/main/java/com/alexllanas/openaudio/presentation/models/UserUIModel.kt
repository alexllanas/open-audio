package com.alexllanas.openaudio.presentation.models

import android.os.Parcelable
import com.alexllanas.core.domain.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUIModel(
    val name: String? = null,
    var avatarUrl: String? = null
) : Parcelable