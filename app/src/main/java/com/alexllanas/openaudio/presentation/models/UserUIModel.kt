package com.alexllanas.openaudio.presentation.models

import android.os.Parcelable
import com.alexllanas.core.domain.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUIModel(
    val name: String? = null,
    val avatarUrl: String? = null
) : Parcelable