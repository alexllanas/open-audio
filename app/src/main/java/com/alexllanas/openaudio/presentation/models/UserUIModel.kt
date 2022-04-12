package com.alexllanas.openaudio.presentation.models

import android.os.Parcelable
import com.alexllanas.core.domain.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUIModel(
    val id: String? = null,
    val name: String? = null,
    var avatarUrl: String? = null,
    val subscriptionCount: Int = 0,
    val subscriberCount: Int = 0,
    val isSubscribing: Boolean = false
) : Parcelable